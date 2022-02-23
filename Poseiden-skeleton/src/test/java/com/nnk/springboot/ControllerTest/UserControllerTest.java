package com.nnk.springboot.ControllerTest;


import com.nnk.springboot.domain.User;

import com.nnk.springboot.dto.UserRequest;
import com.nnk.springboot.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    private static User user;

    @MockBean
    UserService userService;
    @MockBean
    UserDetailsService userDetailsService;

    @BeforeEach
    public void contextLoads(){

        user = new User();
        UserRequest userRequest = new UserRequest();
        user.setId(1);
        user.setUsername("UserNameTest");
        user.setFullname("FullNameTest");
        user.setRole("USER_ROLE");
        user.setPassword("passwordTest");


        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        when(userService.saveUser(userRequest)).thenReturn(user);
    }

    @Test
    public void TestAddUserTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().size(2))
                .andExpect(view().name("/user/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestGetDeleteUSer() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
                .andExpect(redirectedUrl("/user/list"))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(status().is(302));
        verify(userService, times(1)).deleteUserById(1);

    }

    @Test
    public void TestGetUserDeleteButNull() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/user/delete/"))
                .andExpect(redirectedUrl(null))
                .andExpect(status().is(404))
                .andExpect(status().isNotFound());
        verify(userService, times(0)).deleteUserById(1);
    }

    @Test
    public void TestGetUserList() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().size(1))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());

    }

    @Test
    public void TestGetUserListButEmpty() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(model().size(1))
                .andExpect(view().name("user/list"))
                .andExpect(status().isOk());

        verify(userService, times(1)).findAllUser();
        Assertions.assertEquals(userService.findAllUser(), Collections.emptyList());
    }

    @Test
    public void TestGetUserToUpdateById() throws Exception {
        when(userService.getUserById(1)).thenReturn(user);
        mvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().size(1))
                .andExpect(view().name("user/update"))
                .andExpect(status().isOk());

    }
}
