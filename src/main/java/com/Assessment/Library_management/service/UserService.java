package com.Assessment.Library_management.service;

import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.SignUpRequestBean;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> userSignUp(SignUpRequestBean dto);
}
