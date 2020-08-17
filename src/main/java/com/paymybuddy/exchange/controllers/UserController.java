package com.paymybuddy.exchange.controllers;

import com.paymybuddy.exchange.dao.UserDAO;
import com.paymybuddy.exchange.models.User;
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
    UserDAO userDAO;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user){
        boolean created = userDAO.create(user);
        if (created == false)
            return ResponseEntity.noContent().build();

        User ourUser = userDAO.getUserByName(user.getFirstName(),user.getLastName());
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
        return userDAO.read(id);
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userDAO.listAll();
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id,@RequestBody User user){
        User userUpdated = userDAO.read(id);
        if (userUpdated!=null){
            userDAO.update(userUpdated);
        }
        return userUpdated;
    }

    @DeleteMapping("/users/{id}")
    public List<User> deleteUserById(@PathVariable int id) throws SQLException {
        userDAO.delete(id);
        return getUsers();
    }


}
