package com.mms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mms.dto.SignUpDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void signUpTest() throws Exception {
        SignUpDto signUpRequest = new SignUpDto();
        signUpRequest.setUsername("test");
        signUpRequest.setEmail("marcelotdsc@gmail.com");

        String contentRequest = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValueAsString(signUpRequest);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/user/signUp")
                .content(contentRequest)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;
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
