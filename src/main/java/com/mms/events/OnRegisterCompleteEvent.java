package com.mms.events;

import com.mms.model.User;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletRequest;

public class OnRegisterCompleteEvent extends ApplicationEvent {
    private final User user;
    private final HttpServletRequest request;

    public OnRegisterCompleteEvent(User user, HttpServletRequest request) {
        super(user);
        this.user = user;
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public HttpServletRequest getRequest() {
        return request;
    }
}
