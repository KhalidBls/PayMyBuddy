package com.paymybuddy.exchange.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
