package com.paymybuddy.exchange.controllers;
import com.paymybuddy.exchange.models.Description;
import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.services.DescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
public class DescriptionController {

    @Autowired
    DescriptionService descriptionService;

    @PostMapping("/descriptions")
    public ResponseEntity<Void> createDescription(@RequestBody Description description) throws SQLException {
        boolean created = descriptionService.create(description);

        if (created == false)
            return ResponseEntity.noContent().build();

        Description ourDescription = descriptionService.read(description.getId());
        if (ourDescription == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ourDescription.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/descriptions/{id}")
    public Description getDescriptionById(@PathVariable int id){
        return descriptionService.read(id);
    }

    @GetMapping("/descriptions")
    public List<Description> getAllDescription(){
        return descriptionService.listAll();
    }

    @PutMapping("/descriptions/{id}")
    public Description updateDescription(@PathVariable int id) throws SQLException {
        Description descriptionUpdated = descriptionService.read(id);
        if (descriptionUpdated!=null)
            descriptionService.update(descriptionUpdated);

        return descriptionUpdated;
    }

    @DeleteMapping("/descriptions/{id}")
    public List<Description> deleteDescriptionById(@PathVariable int id) throws SQLException {
        descriptionService.delete(id);
        return getAllDescription();
    }

}
