package com.Assessment.Library_management.controller;

import com.Assessment.Library_management.Bean.LoginResponseBean;
import com.Assessment.Library_management.dto.AuthRequestDto;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.entity.User;
import com.Assessment.Library_management.exception.DataNotFounException;
import com.Assessment.Library_management.repository.UserRepository;
import com.Assessment.Library_management.util.EndPoints;
import com.Assessment.Library_management.util.JwtUtil;
import com.Assessment.Library_management.util.MessageVarList;
import com.Assessment.Library_management.util.StatusVarList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@Slf4j
public class TokenController {

    private JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    @PostMapping(value = EndPoints.TOKEN, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseBean getToken(@RequestBody AuthRequestDto authRequest) throws DataNotFounException {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

            if (authenticate.isAuthenticated()) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
                final String token = jwtUtil.generateToken(userDetails);

                User user = userRepository.findByUserName(authRequest.getUserName()).orElseThrow(() -> new DataNotFounException("User Details not found"));
                log.debug("GET user detail " + user);


                String userType = user.getUserType();

                if (!userType.isEmpty()) {
                    log.debug("send  Response: " + new LoginResponseBean(token, userType).toString());
                    return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.SUCCESS, new LoginResponseBean(token, userType));

                } else {
                    //default user type admin set
                    log.debug("send RESPONSE : " + new LoginResponseBean(token, "ADMIN").toString());
                    return new ResponseBean(MessageVarList.RSP_SUCCESS, "success", new LoginResponseBean(token, "ADMIN"));

                }


            } else {
                log.debug("user authenticate field");
                log.debug("send to web portal : " + authenticate, toString());
                return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.FAILED, authenticate);
            }

        } catch (Exception e) {
            log.error("error  " + e.getMessage());
            return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, e.getMessage(), null);
        }


    }


}
