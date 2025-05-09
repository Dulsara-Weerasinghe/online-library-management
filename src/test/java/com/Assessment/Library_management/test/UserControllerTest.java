package com.Assessment.Library_management.test;

import com.Assessment.Library_management.controller.UserController;
import com.Assessment.Library_management.dto.SignUpRequestBean;
import com.Assessment.Library_management.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
//@WebMvcTest(UserController.class)
public class UserControllerTest {


    @Mock
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @InjectMocks
    private ObjectMapper objectMapper;

    @Test
    void testUserSignUpSuccess() throws Exception {

        SignUpRequestBean dto = new SignUpRequestBean();
        dto.setUserName("john_doe");
        dto.setPassword("password123");
        dto.setName("John");
        dto.setEmail("john@example.com");


        // Mock the service method behavior
//        when(userService.userSignUp(dto)).thenReturn(ResponseEntity.ok("UserId123"));


        mockMvc.perform(post("/userSignUp").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk()).andExpect(jsonPath("$.body").value("user00009"));


    }
}
