package com.mms.service;

import com.mms.model.User;
import com.mms.model.VerificationToken;
import com.mms.events.OnRegisterCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MailMessageService {

    @Autowired
    private MessageSource messages;

    @Autowired
    private Environment environment;

    public SimpleMailMessage constructEmailMessage(OnRegisterCompleteEvent event, User user, String token) {
        String contextPath = getAppUrl(event.getRequest());
        String recipientAddress = user.getEmail();
        String subject = "Register Confirmation";
        String confirmationUrl = contextPath + "/registerConfirm?token=" + token;
        String message = messages.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.getRequest().getLocale());
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n\n" + confirmationUrl);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }

    public SimpleMailMessage constructResendVerificationTokenEmail(HttpServletRequest request, VerificationToken newToken, User user) {
        String contextPath = getAppUrl(request);
        String confirmationUrl = contextPath + "/registerConfirm?token=" + newToken.getToken();
        String message = messages.getMessage("message.resendToken", null, request.getLocale());
        return constructEmail("Resend Registration Token", message + " \r\n" + confirmationUrl, user);
    }

    public SimpleMailMessage constructResetTokenEmail(HttpServletRequest request, String token, User user) {
        String contextPath = getAppUrl(request);
        String url = contextPath + "/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword", null, request.getLocale());
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
        return request.getHeader("origin");
//        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
