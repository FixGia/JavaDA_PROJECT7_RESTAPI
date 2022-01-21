package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;

import java.util.List;

public interface UserService {



    public User saveUser (UserRequest user);

    public List<User> findAllUser();

    public User getUserById(Integer id);

    public User updateUser(UserRequest user, Integer id);

    public void deleteUserById(Integer id);
}
