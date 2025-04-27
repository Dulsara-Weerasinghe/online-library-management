package com.Assessment.Library_management.Bean;

import java.io.Serializable;

public class LoginResponseBean implements Serializable {

    private final String jwttoken;
    private final String userType;

    public LoginResponseBean(String jwttoken, String userType) {
        this.jwttoken = jwttoken;
        this.userType = userType;
    }

    public String getToken() {
        return this.jwttoken;
    }


    public String getUserType(){ return this.userType;}
}
