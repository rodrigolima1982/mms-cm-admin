package com.mms.service;

import com.mms.model.PasswordResetToken;
import com.mms.model.User;
import com.mms.model.VerificationToken;
import com.mms.repository.PasswordResetTokenRepository;
import com.mms.repository.UserRepository;
import com.mms.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SessionRegistry sessionRegistry;

    public static  String TOKEN_INVALID = "invalidToken";
    public static  String TOKEN_EXPIRED = "expired";
    public static  String TOKEN_VALID = "valid";

    public Optional<User> getUser( String verificationToken) {
         VerificationToken token = tokenRepository.findByToken(verificationToken);
        if (token != null) {
            return Optional.ofNullable(token.getUser());
//            return token.getUser();
        }
        return null;
    }

    public VerificationToken getVerificationToken( String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    public void saveRegisteredUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserTokens(User user) {
        VerificationToken verificationToken = tokenRepository.findByUser(user);
        if (verificationToken != null) {
            tokenRepository.delete(verificationToken);
        }
        PasswordResetToken passwordToken = passwordTokenRepository.findByUser(user);

        if (passwordToken != null) {
            passwordTokenRepository.delete(passwordToken);
        }
    }

    public void deleteUser(User user) {
        this.deleteUserTokens(user);
        userRepository.delete(user);
    }

    public void createVerificationTokenForUser( User user,  String token) {
         VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    public VerificationToken generateNewVerificationToken( String existingVerificationToken) {
        VerificationToken verificationToken = tokenRepository.findByToken(existingVerificationToken);
        verificationToken.updateToken(UUID.randomUUID()
                .toString());
        verificationToken = tokenRepository.save(verificationToken);
        return verificationToken;
    }

    public void createPasswordResetTokenForUser( User user,  String token) {
         PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    public User findUserByEmail( String email) {
        return userRepository.findByEmail(email);
    }

    public PasswordResetToken getPasswordResetToken( String token) {
        return passwordTokenRepository.findByToken(token);
    }

    public Optional<User> getUserByPasswordResetToken( String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getUser());
    }

    public Optional<User> getUserByID( long id) {
        return userRepository.findById(id);
    }

    public void changeUserPassword( User user,  String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public boolean checkIfValidOldPassword( User user,  String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public String validateVerificationToken(String token) {
         VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

         User user = verificationToken.getUser();
         Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate()
                .getTime() - cal.getTime()
                .getTime()) <= 0) {
            tokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }

    private boolean emailExists( String email) {
        return userRepository.findByEmail(email) != null;
    }

    public List<String> getUsersFromSessionRegistry() {
        return sessionRegistry.getAllPrincipals()
                .stream()
                .filter((u) -> !sessionRegistry.getAllSessions(u, false)
                        .isEmpty())
                .map(o -> {
                    if (o instanceof User) {
                        return ((User) o).getEmail();
                    } else {
                        return o.toString()
                                ;
                    }
                }).collect(Collectors.toList());
    }

}
