package com.Assessment.Library_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestBean {

    private String name;
    private String userName;
    private String password;
    private String email;

}
