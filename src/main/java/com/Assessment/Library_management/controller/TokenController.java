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
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@RestController
public class TokenController {

    private JwtUtil jwtUtil;
    private static final Logger log = LoggerFactory.getLogger(TokenController.class);



    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    @PostMapping(value = EndPoints.TOKEN, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getToken(@RequestBody AuthRequestDto authRequest) throws DataNotFounException {

        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

            if (authenticate.isAuthenticated()) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUserName());
                final String token = jwtUtil.generateToken(userDetails);

                User user = userRepository.findByUserName(authRequest.getUserName()).orElseThrow(() -> new DataNotFounException("User Details not found"));
                log.info("GET user detail " + user);


                String userType = user.getUserType();

                if (!userType.isEmpty()) {
                    log.info("send  Response: " + new LoginResponseBean(token, userType).toString());
                     LoginResponseBean loginResponseBean = new LoginResponseBean(token,userType);

//                    return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.SUCCESS, loginResponseBean);

                    return ResponseEntity.ok(loginResponseBean);
                } else {
                    //default user type admin set
                    log.info("send RESPONSE : " + new LoginResponseBean(token, "ADMIN").toString());
                    LoginResponseBean loginResponseBean = new LoginResponseBean(token,"ADMIN");
//                    return new ResponseBean(MessageVarList.RSP_SUCCESS, "success",loginResponseBean);
                    return ResponseEntity.ok(loginResponseBean);
                }


            } else {
                log.info("user authenticate field");
                log.info("send to web portal : " + authenticate, toString());
//                return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.FAILED, null);
                return ResponseEntity.ok(null);
            }

        } catch (Exception e) {
            log.error("error  " + e.getMessage());
//            return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, e.getMessage(), null);
            return ResponseEntity.ok(null);
        }


    }


}
