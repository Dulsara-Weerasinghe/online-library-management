package com.Assessment.Library_management.service.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;



    @Override
    @Transactional
    public ResponseBean userSignUp(SignUpRequestBean dto) {
        if (!checkUserNameExists(dto.getUserName())) {
            String bcryptPassword = Utility.getBCryptPasswordEncoder(passwordEncoder, dto.getPassword());

            log.debug("New user Registration");
            User newUser = new User();
            newUser.setUserId(Utility.generateUserId());
            newUser.setUserName(dto.getUserName());
            newUser.setCreatedAt(LocalDateTime.now());
            newUser.setEmail(dto.getEmail());
            newUser.setPassword(bcryptPassword);
            newUser.setName(dto.getName());

            User save = userRepository.save(newUser);
            return new ResponseBean(MessageVarList.RSP_SUCCESS, StatusVarList.SUCCESS,save.getId());
        }else{
            return new ResponseBean(MessageVarList.RSP_NO_DATA_FOUND, StatusVarList.FAILED,null);
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
