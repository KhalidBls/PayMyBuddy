package com.paymybuddy.exchange.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.exchange.models.User;
import com.paymybuddy.exchange.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        //ARRANGE
        User user;
        ObjectMapper mapper = new ObjectMapper();
        String userJSON = "{\n" +
                "        \"firstName\": \"khalid\",\n" +
                "        \"lastName\": \"jacky\",\n" +
                "        \"email\": \"jack.jacky@mail.com\",\n" +
                "        \"balance\": 12.0,\n" +
                "        \"password\": \"jackpass\"\n" +
                "    }";
        user = mapper.readValue(userJSON, User.class);

        //ACT
        when(userService.create(any(User.class))).thenReturn(true);
        when(userService.getUserByName(user.getFirstName(),user.getLastName())).thenReturn(user);

        //ASSERT
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON))
                .andExpect(status().is(201));

        assertTrue(user.getFirstName().equals("khalid"));
    }

    @Test
    public void testGetUserById() throws Exception {
        //ARRANGE
        User user = new User("bob","bobby","bob@mail.com",12.0,"bobpass");
        user.setId(77);

        //ACT
        when(userService.read(77)).thenReturn(user);

        //ASSERT
        mockMvc.perform(get("/users/77")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.balance", is(user.getBalance())))
                .andExpect(jsonPath("$.password", is(user.getPassword())));

        verify(userService,times(1)).read(user.getId());
    }

    @Test
    public void testUpdateUser() throws Exception {
        //GIVEN
        User user = new User("bob","bobby","bob@mail.com",12.0,"bobpass");
        user.setId(77);

        String userJSON = "{\n" +
                "        \"firstName\": \"bob\",\n" +
                "        \"lastName\": \"bobby\",\n" +
                "        \"email\": \"jack.jacky@mail.com\",\n" +
                "        \"balance\": 12.0,\n" +
                "        \"password\": \"jackpass\"\n" +
                "    }";

        //WHEN
        when(userService.read(77)).thenReturn(user);
        when(userService.update(user)).thenReturn(true);

        //THEN
        mockMvc.perform(put("/users/77")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON))
                .andExpect(status().is(200));

        verify(userService,times(1)).read(user.getId());
        verify(userService,times(1)).update(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        //GIVEN
        User user = new User("bob","bobby","bob@mail.com",12.0,"bobpass");
        user.setId(77);

        //WHEN
        when(userService.delete(77)).thenReturn(true);
        //THEN
        mockMvc.perform(delete("/users/77")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(userService,times(1)).delete(user.getId());
    }

    @Test
    public void testGetAllUsers() throws Exception {
        //GIVEN
        User user = new User("bob","bobby","bob@mail.com",12.0,"bobpass");
        user.setId(77);
        User user1 = new User("jack","jacky","jack@mail.com",7.0,"jackpass");
        user1.setId(54);

        //WHEN
        when(userService.listAll()).thenReturn(Arrays.asList(user,user1));

        //THEN
        mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(user.getLastName())))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())))
                .andExpect(jsonPath("$[0].balance", is(user.getBalance())))
                .andExpect(jsonPath("$[0].password", is(user.getPassword())))
                .andExpect(jsonPath("$[1].firstName", is(user1.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(user1.getLastName())))
                .andExpect(jsonPath("$[1].email", is(user1.getEmail())))
                .andExpect(jsonPath("$[1].balance", is(user1.getBalance())))
                .andExpect(jsonPath("$[1].password", is(user1.getPassword())));
    }

}
