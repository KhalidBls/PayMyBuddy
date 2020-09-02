package com.paymybuddy.exchange.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.exchange.models.BankAccount;
import com.paymybuddy.exchange.models.User;
import com.paymybuddy.exchange.services.BankAccountService;
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

@WebMvcTest(BankAccountController.class)
@RunWith(SpringRunner.class)
public class BankAccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BankAccountService bankAccountService;

    @Test
    public void testCreateBankAccount() throws Exception {
        //GIVEN
        BankAccount bankAccount;

        ObjectMapper mapper = new ObjectMapper();
        String bankAccountJSON = "{\n" +
                "        \"iban\": \"0000012345\",\n" +
                "        \"swift\": \"FRPP\",\n" +
                "        \"idUser\": 5\n" +
                "    }";
        bankAccount = mapper.readValue(bankAccountJSON,BankAccount.class);

        //WHEN
        when(bankAccountService.create(any(BankAccount.class))).thenReturn(true);
        when(bankAccountService.read(any(Integer.class))).thenReturn(bankAccount);


        //THEN
        mockMvc.perform(post("/bankAccounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bankAccountJSON))
                .andExpect(status().is(201));

    }

}
