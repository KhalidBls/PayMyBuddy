package com.paymybuddy.exchange.controllers;

import com.paymybuddy.exchange.models.BankAccount;
import com.paymybuddy.exchange.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;

    @PostMapping("/bankAccounts")
    public ResponseEntity<Void> createBankAccount(@RequestBody BankAccount bankAccount) throws SQLException {
        boolean created = bankAccountService.create(bankAccount);

        if (created == false)
            return ResponseEntity.noContent().build();

        BankAccount ourBankAccount = bankAccountService.read(bankAccount.getId());
        if (ourBankAccount == null)
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ourBankAccount.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/bankAccounts/{id}")
    public BankAccount getBankAccountById(@PathVariable int id){
        return bankAccountService.read(id);
    }

    @GetMapping("/bankAccounts")
    public List<BankAccount> getAllBankAccounts(){
        return bankAccountService.listAll();
    }

    @PutMapping("/bankAccounts/{id}")
    public BankAccount updateBankAccount(@PathVariable int id) throws SQLException {
        BankAccount bankAccountUpdated = bankAccountService.read(id);
        if (bankAccountUpdated!=null)
            bankAccountService.update(bankAccountUpdated);

        return bankAccountUpdated;
    }

    @DeleteMapping("/bankAccounts/{id}")
    public List<BankAccount> deleteBankAccountsById(@PathVariable int id) throws SQLException {
        bankAccountService.delete(id);
        return getAllBankAccounts();
    }

}
