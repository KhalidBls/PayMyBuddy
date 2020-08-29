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

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

}
