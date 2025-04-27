package com.Assessment.Library_management.controller;

import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.SignUpRequestBean;
import com.Assessment.Library_management.service.UserService;
import com.Assessment.Library_management.util.Utility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@Slf4j
public class UserController {


    @Autowired
    private static UserService userService;
    /**
     * For user registration
     * */

    @PostMapping(value = "/userSignUp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBean signUp(@RequestBody SignUpRequestBean dto)  {
        log.debug("Received User SignUp Request - {} ");
        ResponseBean responseBean = userService.userSignUp(dto);
        log.debug("Received User SignUp Response - {} ", dto);
        return responseBean;
    }


}
