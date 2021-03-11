package com.mms.controller;

import com.mms.dto.PasswordDto;
import com.mms.exception.InvalidOldPasswordException;
import com.mms.model.ERole;
import com.mms.model.Role;
import com.mms.model.User;
import com.mms.model.VerificationToken;
import com.mms.repository.RoleRepository;
import com.mms.repository.UserRepository;
import com.mms.security.payload.request.SignupRequest;
import com.mms.security.payload.response.MessageResponse;
import com.mms.security.registration.OnRegistrationCompleteEvent;
import com.mms.service.MailMessageService;
import com.mms.service.PasswordResetTokenService;
import com.mms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messages;

    @Autowired
    private MailMessageService mailMessageService;

    // TODO: passar lógica para o UserService
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User registeredUser = userRepository.save(user);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, request.getLocale()));

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/resendRegistrationToken")
    public ResponseEntity<?> resendRegistrationToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        User user = userService.getUser(newToken.getToken());
        mailSender.send(mailMessageService.constructResendVerificationTokenEmail(request, request.getLocale(), newToken, user));
        // TODO: i18n
//        return ResponseEntity.ok(messages.getMessage("message.resendRegistrationToken", null, request.getLocale()));
        return ResponseEntity.ok(new MessageResponse("User resendRegistrationToken successfully!"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        User user = userService.findUserByEmail(userEmail);
        if (user != null) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(mailMessageService.constructResetTokenEmail(request, request.getLocale(), token, user));
        }
        return ResponseEntity.ok(new MessageResponse("User resetPassword successfully!"));
    }

    @PostMapping("/savePassword")
    public ResponseEntity<?> savePassword(@Valid PasswordDto passwordDto) {
        String result = passwordResetTokenService.validatePasswordResetToken(passwordDto.getToken());

        if (result != null) {
            return ResponseEntity.ok(new MessageResponse("User savePassword " + result + "!"));
        }

        Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if (user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return ResponseEntity.ok(new MessageResponse("User savePassword successfully!"));
        } else {
            return ResponseEntity.ok(new MessageResponse("User savePassword invalid!"));
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<?> changeUserPassword(@Valid PasswordDto passwordDto) {
        User user = userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("User updatePassword successfully!"));
    }

}
