package com.paymybuddy.exchange.controllers;

import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Void> createTransaction(@RequestBody Transaction transaction) throws SQLException {
        boolean created = transactionService.create(transaction);

        if (created == false)
            return ResponseEntity.noContent().build();

        transaction.setId(transactionService.getTransactionByIdUserAndSender(transaction.getIdUserSender(),transaction.getIdUserReceiver()).getId());
        Transaction ourTransaction = transactionService.read(transaction.getId());
        if (ourTransaction == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ourTransaction.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/transactions/{id}")
    public Transaction getTransactionById(@PathVariable int id){
        return transactionService.read(id);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(){
        return transactionService.listAll();
    }

    @PutMapping("/transactions/{id}")
    public Transaction updateTransaction(@PathVariable int id,@RequestBody Transaction transaction) throws SQLException {
        Transaction transactionUpdated = transactionService.read(id);
        if(transactionUpdated!=null)
            transactionService.update(transactionUpdated);

        return transactionUpdated;
    }

    @DeleteMapping("/transactions/{id}")
    public List<Transaction> deleteTransactionById(@PathVariable int id) throws SQLException {
        transactionService.delete(id);
        return getTransactions();
    }

}
