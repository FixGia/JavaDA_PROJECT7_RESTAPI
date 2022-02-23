package com.nnk.springboot.ServiceTest;


import com.nnk.springboot.config.PasswordEncoder;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserRequest;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.service.Impl.UserServiceImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private User user ;
    private UserRequest userRequest;
    private UserRequest userToSave;
    private Authentication authentication;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void setUp(){
        user = new User();
        user.setId(1);
        user.setFullname("Jean");
        user.setUsername("Test");


        userRequest = new UserRequest();
        userRequest.setId(1);
        userRequest.setUsername("Test2");
        userRequest.setFullname("Test2");
        user.setRole("USER");
        userRequest.setPassword(user.getPassword());

        authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

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
    public void updateUserTest(){

        when(userRepository
                .findById(anyInt()))
                .thenReturn(java.util.Optional.ofNullable(user));

        if(user !=null) {
            userService.updateUser(userRequest, 1);
        } else
        {
            verify(userRepository, times(0)).save(user);
        }


    }

    @Test
    public void saveUserTest(){

        userService.saveUser(userRequest);
        verify(userRepository,times(1)).save(any(User.class));

    }


}
