package com.paymybuddy.exchange.controllers;

import com.paymybuddy.exchange.models.UserRelationship;
import com.paymybuddy.exchange.services.UserRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
public class UserRelationshipController {

    @Autowired
    UserRelationshipService userRelationshipService;

    @PostMapping("/relationships")
    public ResponseEntity<Void> createRelationship(@RequestBody UserRelationship userRelationship) throws SQLException {

        if (userRelationshipService.verifyRelationship(userRelationship.getIdUserRelating(),userRelationship.getIdUserRelated()))
            return ResponseEntity.noContent().build();

        boolean created = userRelationshipService.create(userRelationship);

        if (created == false)
            return ResponseEntity.noContent().build();

        UserRelationship ourUserRelationship = userRelationshipService.read(userRelationship.getId());
        if (ourUserRelationship == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ourUserRelationship.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/relationships/{id}")
    public UserRelationship getRelationshipById(@PathVariable int id){
        return userRelationshipService.read(id);
    }

    @GetMapping("/relationships")
    public List<UserRelationship> getAllRelationship(){
        return userRelationshipService.listAll();
    }

    @PutMapping("/relationships/{id}")
    public UserRelationship updateRelationship(@PathVariable int id) throws SQLException {
        UserRelationship userRelationshipUpdated = userRelationshipService.read(id);
        if (userRelationshipUpdated!=null)
            userRelationshipService.update(userRelationshipUpdated);

        return userRelationshipUpdated;
    }

    @DeleteMapping("/relationships/{id}")
    public List<UserRelationship> deleteRelationship(@PathVariable int id) throws SQLException {
        userRelationshipService.delete(id);
        return getAllRelationship();
    }

}
