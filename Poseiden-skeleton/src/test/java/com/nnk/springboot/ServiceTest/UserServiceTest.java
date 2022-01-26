package com.nnk.springboot.ServiceTest;


import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;
import com.nnk.springboot.exception.DataNotFoundException;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User user ;
    private UserRequest userRequest;


    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(1);
        user.setFullName("Jean");
        user.setUsername("Test");
        user.setRole("USER");
        user.setPassword("password");

        userRequest = new UserRequest();
        userRequest.setUsername("Test2");
        userRequest.setFullname("Test2");
        userRequest.setPassword("password2");

        lenient().when(userRepository.save(user)).thenReturn(user);
    }

    @Test
    public void findUserByIdTest(){
        lenient().when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));
        userService.getUserById(user.getId());
        verify(userRepository, times(1)).findById(1);
    }


    @Test
    public void deleteUserByIdTest(){
        userService.deleteUserById(1);
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void getUsersTest(){
        userService.findAllUser();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void saveUserTest(){

        userService.saveUser(userRequest);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void updateUserTest(){
        lenient().when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(user));
        userService.updateUser(userRequest, 1);
        verify(userRepository, times(1)).save(user);
    }
}
