package com.paymybuddy.exchange.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.exchange.models.Transaction;
import com.paymybuddy.exchange.services.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
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

@WebMvcTest(TransactionController.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    @Test
    public void testCreateTransaction() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        //GIVEN
        Transaction transaction;

        ObjectMapper mapper = new ObjectMapper();
        String transactionJSON = "{\n" +
                "        \"amount\": \"13\",\n" +
                "        \"idUserSender\": \"13\",\n" +
                "        \"idUserReceiver\": \"13\",\n" +
                "        \"idDescription\": \"2\",\n" +
                "        \"type\": \"REFUND\"\n" +
                "    }";
        transaction = mapper.readValue(transactionJSON,Transaction.class);

        //WHEN
        when(transactionService.create(any(Transaction.class))).thenReturn(true);
        when(transactionService.read(any(Integer.class))).thenReturn(transaction);

        //THEN
        mockMvc.perform(post("/transactions")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionJSON))
                .andExpect(status().is(201));
    }

    @Test
    public void testGetTransactionById() throws Exception {
        //GIVEN
        Transaction transaction = new Transaction( 12.8, 4, 1, 2, "Remboursement");
        transaction.setId(2);


        //WHEN
        when(transactionService.read(2)).thenReturn(transaction);

        //THEN
        mockMvc.perform(get("/transactions/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(transaction.getId())))
                .andExpect(jsonPath("$.amount", is(transaction.getAmount())))
                .andExpect(jsonPath("$.idUserSender", is(transaction.getIdUserSender())))
                .andExpect(jsonPath("$.idUserReceiver", is(transaction.getIdUserReceiver())))
                .andExpect(jsonPath("$.idDescription", is(transaction.getIdDescription())));
    }

    @Test
    public void testUpdateDescription() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        //GIVEN
        Transaction transaction = new Transaction( 12.8, 4, 1, 2, "Remboursement");
        transaction.setId(2);

        String transactionJSON = "{\n" +
                "        \"amount\": \"13\",\n" +
                "        \"idUserSender\": \"13\",\n" +
                "        \"idUserReceiver\": \"13\",\n" +
                "        \"idDescription\": \"2\",\n" +
                "        \"type\": \"REFUND\"\n" +
                "    }";

        //WHEN
        when(transactionService.read(2)).thenReturn(transaction);
        when(transactionService.update(transaction)).thenReturn(true);

        //THEN
        mockMvc.perform(put("/transactions/2")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionJSON))
                .andExpect(status().is(200));

        verify(transactionService,times(1)).read(transaction.getId());
        verify(transactionService,times(1)).update(transaction);
    }

    @Test
    public void testDeleteTransaction() throws Exception {
        String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
        CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());
        //GIVEN
        Transaction transaction = new Transaction( 12.8, 4, 1, 2, "Remboursement");
        transaction.setId(2);

        //WHEN
        when(transactionService.delete(2)).thenReturn(true);

        //THEN
        mockMvc.perform(delete("/transactions/2")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        verify(transactionService,times(1)).delete(transaction.getId());
    }

    @Test
    public void testGetAllTransaction() throws Exception {
        //GIVEN
        Transaction transaction = new Transaction( 12.8, 4, 1, 2, "Remboursement");
        transaction.setId(2);
        Transaction transaction1 = new Transaction( 12.8, 4, 1, 2, "paiement");
        transaction.setId(3);

        //WHEN
        when(transactionService.listAll()).thenReturn(Arrays.asList(transaction,transaction1));

        //THEN
        mockMvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(2)))
                .andExpect(jsonPath("$[0].type", is(transaction.getType())))
                .andExpect(jsonPath("$[1].type", is(transaction1.getType())));
    }



}
