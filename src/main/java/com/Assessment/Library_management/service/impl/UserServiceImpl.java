package com.Assessment.Library_management.service.impl;

import com.Assessment.Library_management.controller.BookController;
import com.Assessment.Library_management.dto.ResponseBean;
import com.Assessment.Library_management.dto.SignUpRequestBean;
import com.Assessment.Library_management.entity.User;
import com.Assessment.Library_management.repository.UserRepository;
import com.Assessment.Library_management.service.UserService;
import com.Assessment.Library_management.util.MessageVarList;
import com.Assessment.Library_management.util.StatusVarList;
import com.Assessment.Library_management.util.Utility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private  PasswordEncoder passwordEncoder;
    private  UserRepository userRepository;


    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Override
    @Transactional
    public ResponseEntity<?> userSignUp(SignUpRequestBean dto) {
        if (!checkUserNameExists(dto.getUserName())) {
            String bcryptPassword = Utility.getBCryptPasswordEncoder(passwordEncoder, dto.getPassword());

            log.info("New user Registration");
            User newUser = new User();
            newUser.setUserId(Utility.generateUserId());
            newUser.setUserName(dto.getUserName());
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setEmail(dto.getEmail());
            newUser.setPassword(bcryptPassword);
            newUser.setName(dto.getName());
            newUser.setUserType("customer");

            User save = userRepository.save(newUser);
//            return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.SUCCESS,save.getId());
            return ResponseEntity.ok(save.getId());
        }else{
            return ResponseEntity.ok(StatusVarList.ALREADY_REGISTERD);
//            return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.FAILED,null);
        }
    }

    public boolean checkUserNameExists(String username) {
        List<User> userList = userRepository.findAll();
        for (User user : userList
        ) {
            if (user.getUserName() != null && user.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
