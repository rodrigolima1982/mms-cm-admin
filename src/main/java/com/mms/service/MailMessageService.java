package com.mms.service;

import com.mms.model.User;
import com.mms.model.VerificationToken;
import com.mms.security.registration.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Service
public class MailMessageService {

    @Autowired
    private MessageSource messages;

    @Autowired
    private Environment environment;

    public SimpleMailMessage constructEmailMessage(OnRegistrationCompleteEvent event, User user, String token) {
        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl = "http://localhost:4200/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n\n" + confirmationUrl);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }

    public SimpleMailMessage constructResendVerificationTokenEmail(HttpServletRequest request, Locale locale, VerificationToken newToken, User user) {
        String contextPath = getAppUrl(request);
        String confirmationUrl = contextPath + "/registrationConfirm.html?token=" + newToken.getToken();
        String message = messages.getMessage("message.resendToken", null, locale);
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    public SimpleMailMessage constructResetTokenEmail(HttpServletRequest request, Locale locale, String token, User user) {
        String contextPath = getAppUrl(request);
        String url = contextPath + "/user/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
