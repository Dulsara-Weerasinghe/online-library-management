package com.Assessment.Library_management.service;

import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.SignUpRequestBean;

public interface UserService {
    ResponseBean userSignUp(SignUpRequestBean dto);
}
