package com.paymybuddy.exchange.controllers;

import com.paymybuddy.exchange.models.User;
import com.paymybuddy.exchange.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody User user) throws SQLException {
        boolean created = userService.create(user);
        if (created == false)
           return ResponseEntity.noContent().build();

        User ourUser = userService.getUserByName(user.getFirstName(),user.getLastName());
        if (ourUser == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ourUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id){
        return userService.read(id);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.listAll();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id) throws SQLException {
        User userUpdated = userService.read(id);
        if (userUpdated!=null)
            userService.update(userUpdated);

        return userUpdated;
    }

    @DeleteMapping("/users/{id}")
    public List<User> deleteUserById(@PathVariable int id) throws SQLException {
        userService.delete(id);
        return getUsers();
    }


}
