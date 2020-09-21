package com.ebanx.homeassigment.assignment.controller;

import com.ebanx.homeassigment.assignment.exceptions.AccountNotFoundException;
import com.ebanx.homeassigment.assignment.model.Account;
import com.ebanx.homeassigment.assignment.model.Event;
import com.ebanx.homeassigment.assignment.model.response.DepositResponse;
import com.ebanx.homeassigment.assignment.model.response.TransferResponse;
import com.ebanx.homeassigment.assignment.model.response.WithdrawResponse;
import com.ebanx.homeassigment.assignment.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.ebanx.homeassigment.assignment.enums.Action.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnCreatedWithCorrectBody() throws Exception {
        Event event = new Event();
        event.setDestination("100");
        event.setAmount(10);
        event.setType(DEPOSIT);

        DepositResponse depositResponse = new DepositResponse();
        depositResponse.setDestination(new Account("100", 10));


        when(transactionService.deposit(any())).thenReturn(depositResponse);

        mockMvc.perform(
                post("/event")
                        .contentType("application/json")
                    .content(objectMapper.writeValueAsString(event)))
        .andExpect(status().isCreated())
        .andExpect(content().string(objectMapper.writeValueAsString(depositResponse)));
    }

    @Test
    public void shouldReturn404ForAccountNotfound() throws Exception {
        Event event = new Event();
        event.setDestination("100");
        event.setAmount(10);
        event.setType(WITHDRAW);

        when(transactionService.withDraw(any())).thenThrow(new AccountNotFoundException());
        mockMvc.perform(
                post("/event")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("0"));
    }

    @Test
    public void shouldReturn201ForWithDrawWithCorrectBody() throws Exception {
        Event event = new Event();
        event.setDestination("100");
        event.setAmount(10);
        event.setType(WITHDRAW);

        WithdrawResponse withdrawResponse = new WithdrawResponse();
        withdrawResponse.setOrigin(new Account("100", 10));

        when(transactionService.withDraw(any())).thenReturn(withdrawResponse);
        mockMvc.perform(
                post("/event")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(withdrawResponse)));
    }

    @Test
    public void shouldReturn200ForReset() throws Exception {
        mockMvc.perform(
                post("/reset")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    public void shouldReturn200ForBalanceWithCorrectBody() throws Exception {
        when(transactionService.getBalance(any())).thenReturn(5);

        mockMvc.perform(
                get("/balance?account_id=10")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void shouldReturn201ForTransfer() throws Exception {
        Event event = new Event();
        event.setDestination("300");
        event.setOrigin("100");
        event.setAmount(10);
        event.setType(TRANSFER);

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setOrigin(new Account("100", 0));
        transferResponse.setDestination(new Account("300", 10));

        when(transactionService.transfer(any())).thenReturn(transferResponse);
        mockMvc.perform(
                post("/event")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(transferResponse)));
    }

}