package com.paymybuddy.exchange.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.exchange.models.BankAccount;
import com.paymybuddy.exchange.services.BankAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @Test
    public void testGetBankAccountById() throws Exception {
        //GIVEN
        BankAccount bankAccount = new BankAccount("FR760012345","BOUSSS",2);
        bankAccount.setId(2);

        //WHEN
        when(bankAccountService.read(2)).thenReturn(bankAccount);

        //THEN
        mockMvc.perform(get("/bankAccounts/2")
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(bankAccount.getId())))
                .andExpect(jsonPath("$.iban", is(bankAccount.getIban())))
                .andExpect(jsonPath("$.swift", is(bankAccount.getSwift())));
    }

    @Test
    public void testUpdateBankAccount() throws Exception {
        //GIVEN
        BankAccount bankAccount = new BankAccount("FR760012345","BOUSSS",2);
        bankAccount.setId(2);

        String userJSON = "{\n" +
                "        \"iban\": \"FR760012345\",\n" +
                "        \"swift\": \"bobby\",\n" +
                "        \"idUser\": 2,\n" +
                "    }";

        //WHEN
        when(bankAccountService.read(2)).thenReturn(bankAccount);
        when(bankAccountService.update(bankAccount)).thenReturn(true);

        //THEN
        mockMvc.perform(put("/bankAccounts/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJSON))
                .andExpect(status().is(200));

        verify(bankAccountService,times(1)).read(bankAccount.getId());
        verify(bankAccountService,times(1)).update(bankAccount);
    }

    @Test
    public void testDeleteBankAccount() throws Exception {
        //GIVEN
        BankAccount bankAccount = new BankAccount("FR760012345","BOUSSS",2);
        bankAccount.setId(2);

        //WHEN
        when(bankAccountService.delete(2)).thenReturn(true);
        //THEN
        mockMvc.perform(delete("/bankAccounts/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(bankAccountService,times(1)).delete(bankAccount.getId());
    }

    @Test
    public void testGetAllBankAccounts() throws Exception {
        //GIVEN
        BankAccount bankAccount1 = new BankAccount("FR760012345","BOUSSS",2);
        bankAccount1.setId(2);
        BankAccount bankAccount2 = new BankAccount("FR7654321","BOUSSS",6);
        bankAccount2.setId(3);

        //WHEN
        when(bankAccountService.listAll()).thenReturn(Arrays.asList(bankAccount1,bankAccount2));

        //THEN
        mockMvc.perform(get("/bankAccounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].iban", is(bankAccount1.getIban())))
                .andExpect(jsonPath("$[0].swift", is(bankAccount1.getSwift())))
                .andExpect(jsonPath("$[0].idUser", is(bankAccount1.getIdUser())))
                .andExpect(jsonPath("$[1].iban", is(bankAccount2.getIban())))
                .andExpect(jsonPath("$[1].swift", is(bankAccount2.getSwift())))
                .andExpect(jsonPath("$[1].idUser", is(bankAccount2.getIdUser())));
    }


}
