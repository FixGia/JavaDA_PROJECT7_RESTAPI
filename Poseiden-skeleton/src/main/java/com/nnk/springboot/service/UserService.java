package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;

import java.util.List;
import java.util.Map;

public interface UserService {



    public User saveUser (UserRequest user);

    public List<User> findAllUser();

    public User findByUserName(String username);

    public User getUserById(Integer id);

    public User updateUser(UserRequest user, Integer id);

    public void deleteUserById(Integer id);


}
