package com.ebanx.homeassigment.assignment.controller;

import com.ebanx.homeassigment.assignment.exceptions.AccountNotFoundException;
import com.ebanx.homeassigment.assignment.model.Account;
import com.ebanx.homeassigment.assignment.model.request.EventDTO;
import com.ebanx.homeassigment.assignment.model.response.DepositResponse;
import com.ebanx.homeassigment.assignment.model.response.TransferResponse;
import com.ebanx.homeassigment.assignment.model.response.WithdrawResponse;
import com.ebanx.homeassigment.assignment.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.ebanx.homeassigment.assignment.enums.Action.*;
import static com.ebanx.homeassigment.assignment.utils.Paths.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TransactionController.class)
public class TransactionControllerTest {

    public static final String ACCOUNT_ID_100 = "100";
    public static final String ACCOUNT_ID_300 = "300";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnCreatedWithCorrectBody() throws Exception {
        EventDTO event = new EventDTO(DEPOSIT, null, ACCOUNT_ID_100, 10);
        DepositResponse depositResponse = new DepositResponse(new Account(ACCOUNT_ID_100, 10));

        when(transactionService.deposit(any())).thenReturn(depositResponse);

        mockMvc.perform(
                post(EVENT)
                        .contentType(APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(event)))
        .andExpect(status().isCreated())
        .andExpect(content().string(objectMapper.writeValueAsString(depositResponse)));
    }

    @Test
    public void shouldReturn404ForAccountNotfound() throws Exception {
        EventDTO event = new EventDTO(WITHDRAW, ACCOUNT_ID_100, null, 10);

        when(transactionService.withDraw(any())).thenThrow(new AccountNotFoundException());
        mockMvc.perform(
                post(EVENT)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("0"));
    }

    @Test
    public void shouldReturn201ForWithDrawWithCorrectBody() throws Exception {
        EventDTO event = new EventDTO(WITHDRAW, null, ACCOUNT_ID_100, 10);
        WithdrawResponse withdrawResponse = new WithdrawResponse(new Account(ACCOUNT_ID_100, 10));

        when(transactionService.withDraw(any())).thenReturn(withdrawResponse);
        mockMvc.perform(
                post(EVENT)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(withdrawResponse)));
    }

    @Test
    public void shouldReturn200ForReset() throws Exception {
        mockMvc.perform(
                post(RESET)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));
    }

    @Test
    public void shouldReturn200ForBalanceWithCorrectBody() throws Exception {
        when(transactionService.getBalance(any())).thenReturn(5);

        mockMvc.perform(
                get(BALANCE + "?account_id=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void shouldReturn201ForTransfer() throws Exception {
        EventDTO event = new EventDTO(TRANSFER, ACCOUNT_ID_100, ACCOUNT_ID_300, 10);
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setOrigin(new Account(ACCOUNT_ID_100, 0));
        transferResponse.setDestination(new Account(ACCOUNT_ID_300, 10));

        when(transactionService.transfer(any())).thenReturn(transferResponse);
        mockMvc.perform(
                post(EVENT)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isCreated())
                .andExpect(content().string(objectMapper.writeValueAsString(transferResponse)));
    }

}