package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;

import java.util.List;

public interface UserService {



    User saveUser (UserRequest user);

    List<User> findAllUser();

    User getUserById(Integer id);

    void updateUser(UserRequest user, Integer id);

    void deleteUserById(Integer id);


}
