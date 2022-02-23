package com.nnk.springboot.service.Impl;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(UserRequest user) {

        try {
            User userToAdd = new User();
            userToAdd.setUsername(user.getUsername());
            userToAdd.setFullname(user.getFullname());
            userToAdd.setPassword(passwordEncoder.encode(user.getPassword()));
            userToAdd.setRole(user.getRole());
            log.info("add a new User");
            userRepository.save(userToAdd);
            return userToAdd;
        } catch (NotConformDataException exception) {
            log.error("cant add a new User");
            return null;
        }
    }

    @Override
    public List<User> findAllUser() {


        try {
            log.info("displayed UserList");
            return userRepository.findAll();
        } catch (DataNotFoundException e)
        {
            log.error("User List doesn't exist in DB ");
            return Collections.emptyList();
        }
    }


    public User findByUserName(String username) {

        try {
            User getUser = userRepository.findByUsername(username);
            log.info("User with id {} was foudn", username);
            return getUser;
        } catch (DataNotFoundException e) {
            log.error("User with id{} doesn't exist in DB", username);
            return null;
        }
    }


    @Override
    public User getUserById(Integer id) {

        try {
            Optional<User> getUser = userRepository.findById(id);
            log.info("User with id {} was found", id);
            return getUser.get();
        } catch (DataNotFoundException e) {
            log.error("User with id{} doesn't exist in DB", id);
            return null;
        }
    }

    @Override
    public void updateUser(UserRequest user, Integer id) {


        Optional<User> userToUpdate = userRepository.findById(id);

        if (userToUpdate.isPresent()) {

            userToUpdate.get().setId(id);

            String fullName = user.getFullname();
            if (fullName != null) {
                userToUpdate.get().setFullname(fullName);
            }
            String userName = user.getUsername();
            if (userName != null) {
                userToUpdate.get().setUsername(userName);
            }
            String password = user.getPassword();
            if (password != null) {
                userToUpdate.get().setPassword(passwordEncoder.encode(password));
            }
            userToUpdate.get().setRole(user.getRole());
            userRepository.save(userToUpdate.get());
            log.info("User with id {} was updated", user.getId());
            userToUpdate.get();
            return;
        }
        throw new DataNotFoundException("User doesn't found in DB");
    }

    @Override
    public void deleteUserById(Integer id) {


        try {
            userRepository.deleteById(id);

        } catch (DataNotFoundException e) {
            log.error("User with id {} doesn't found in BD", id);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            log.error("User not Found in the DB");
            throw new UsernameNotFoundException("User not Found in Db");
        } else {
            log.info("User {} found in the Db ", username);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
            }
        }

}

