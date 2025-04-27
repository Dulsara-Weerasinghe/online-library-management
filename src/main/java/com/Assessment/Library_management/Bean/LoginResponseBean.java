package com.Assessment.Library_management.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data

public class LoginResponseBean implements Serializable {

    private final String jwttoken;
    private final String userType;
    private LoginResponseBean data;

    public LoginResponseBean(String jwttoken, String userType) {
        this.jwttoken = jwttoken;
        this.userType = userType;
    }


}
