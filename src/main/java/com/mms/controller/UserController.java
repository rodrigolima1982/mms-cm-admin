package com.mms.controller;

import com.mms.dto.PasswordDto;
import com.mms.dto.SignUpConfirmDto;
import com.mms.exception.InvalidOldPasswordException;
import com.mms.model.ERole;
import com.mms.model.Role;
import com.mms.model.User;
import com.mms.model.VerificationToken;
import com.mms.repository.RoleRepository;
import com.mms.dto.SignUpDto;
import com.mms.security.payload.response.MessageResponse;
import com.mms.events.OnRegisterCompleteEvent;
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
    public ResponseEntity<?> signUp(HttpServletRequest request, @Valid @RequestBody SignUpDto signUpDto) {
        if (userService.existsByUsername(signUpDto.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userService.existsByEmail(signUpDto.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpDto.getUsername(),
                signUpDto.getEmail(),
                encoder.encode(signUpDto.getPassword()));

        Set<String> strRoles = signUpDto.getRole();
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
        User registeredUser = userService.create(user);

        eventPublisher.publishEvent(new OnRegisterCompleteEvent(registeredUser, request));

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signUpConfirm")
    public ResponseEntity<?> signUpConfirm(@Valid @RequestBody SignUpConfirmDto signUpConfirmDto) {
        String result = userService.validateVerificationToken(signUpConfirmDto.getToken());
        if (result.equals("valid")) {
            if (!signUpConfirmDto.getPassword().equals(signUpConfirmDto.getConfirmPassword())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid confirmation password!"));
            }
            Optional<User> user = userService.getUser(signUpConfirmDto.getToken());
            if (user.isPresent()) {
                User u = user.get();
                u.setEnabled(true);
                u.setPassword(encoder.encode(signUpConfirmDto.getPassword()));
                this.userService.saveRegisteredUser(u);
                this.userService.deleteUserTokens(u);
                return ResponseEntity.ok(new MessageResponse("User confirmed!"));
            }
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Error: invalid token!"));
    }

    @GetMapping("/resendRegisterToken")
    public ResponseEntity<?> resendRegisterToken(HttpServletRequest request, @RequestParam("token") String existingToken) {
        VerificationToken newToken = userService.generateNewVerificationToken(existingToken);
        Optional<User> user = userService.getUser(newToken.getToken());
        mailSender.send(mailMessageService.constructResendVerificationTokenEmail(request, newToken, user.get()));
        // TODO: i18n
//        return ResponseEntity.ok(messages.getMessage("message.resendRegisterToken", null, request.getLocale()));
        return ResponseEntity.ok(new MessageResponse("User resendRegisterToken successfully!"));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
        Optional<User> user = userService.findUserByEmail(userEmail);
        if (user.isPresent()) {
            String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user.get(), token);
            mailSender.send(mailMessageService.constructResetTokenEmail(request, token, user.get()));
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
        Optional<User> user = userService.findUserByEmail(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail());
        if (!userService.checkIfValidOldPassword(user.get(), passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
        return ResponseEntity.ok(new MessageResponse("User updatePassword successfully!"));
    }

}
