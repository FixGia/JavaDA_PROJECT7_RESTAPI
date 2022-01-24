package com.nnk.springboot.service.Impl;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.exception.NotConformDataException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(UserRequest user) {

        try {
            User userToAdd = new User();
            userToAdd.setUsername(user.getUsername());
            userToAdd.setFullName(user.getFullname());
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userToAdd.setPassword(encoder.encode(user.getPassword()));
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
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) {

        Optional<User> getUser = userRepository.findById(id);
        return getUser.get();
    }

    @Override
    public User updateUser(UserRequest user, Integer id) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> userToUpdate = userRepository.findById(id);

        if (userToUpdate.isPresent()) {

            userToUpdate.get().setId(id);

            String fullName = user.getFullname();
            if (fullName != null) {
                userToUpdate.get().setFullName(fullName);
            }
            String userName = user.getUsername();
            if (userName != null) {
                userToUpdate.get().setUsername(userName);
            }
            String password = user.getPassword();
            if (password != null) {
                userToUpdate.get().setPassword(encoder.encode(password));
            }
            userToUpdate.get().setRole(user.getRole());
            userRepository.save(userToUpdate.get());
            log.info("User with id {} was updated", user.getId());
            return userToUpdate.get();
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
}
