package com.mms.security.registration;

import com.mms.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final User user;
    private final Locale locale;

    public OnRegistrationCompleteEvent(final User user, final Locale locale) {
        super(user);
        this.user = user;
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }
}
