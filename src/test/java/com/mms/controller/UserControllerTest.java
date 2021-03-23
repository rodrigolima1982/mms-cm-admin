package com.mms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mms.dto.CreateTemplateDto;
import com.mms.dto.SignUpDto;
import com.mms.events.OnRegisterCompleteEvent;
import com.mms.model.ERole;
import com.mms.model.Role;
import com.mms.model.User;
import com.mms.repository.RoleRepository;
import com.mms.service.TemplateService;
import com.mms.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService service;

    @Mock
    PasswordEncoder encoder;

    @Mock
    RoleRepository roleRepository;

    @Mock
    ApplicationEventPublisher eventPublisher;

    @InjectMocks
    UserController userController;

    private SignUpDto signUpDto = new SignUpDto();
    private Role role = new Role();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        signUpDto.setUsername("test");
        signUpDto.setEmail("test@mms.com");

        role.setId(ERole.ROLE_USER.id());
        role.setName(ERole.ROLE_USER);
    }

    @Test
    void signUpTest() throws Exception {
        given(service.existsByUsername(any(String.class))).willReturn(false);
        given(service.create(any(User.class))).willReturn(new User());
        given(encoder.encode(any(String.class))).willReturn("");
        given(roleRepository.findByName(any(ERole.class))).willReturn(java.util.Optional.ofNullable((this.role)));

        String contentRequest = new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS).writeValueAsString(signUpDto);

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
