package com.mms.events.listener;

import com.mms.events.OnRegisterCompleteEvent;
import com.mms.model.User;
import com.mms.service.MailMessageService;
import com.mms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegisterListener implements ApplicationListener<OnRegisterCompleteEvent> {
    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private MailMessageService mailMessageService;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void onApplicationEvent(final OnRegisterCompleteEvent event) {
        this.confirmRegister(event);
    }

    private void confirmRegister(final OnRegisterCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        final SimpleMailMessage email = mailMessageService.constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

}
