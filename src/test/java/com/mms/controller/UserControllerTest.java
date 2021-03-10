package com.mms.controller;

import com.mms.dto.SignUpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    private SignUpDto signUpRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void signUpTest() throws Exception {
    }

    @Test
    void resendRegisterTokenTest() throws Exception {
    }

    @Test
    void resetPasswordTest() throws Exception {
    }

    @Test
    void savePasswordTest() throws Exception {
    }

    @Test
    void changeUserPasswordTest() throws Exception {
    }

}
