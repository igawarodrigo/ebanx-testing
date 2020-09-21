package com.ebanx.homeassigment.assignment.service;

import com.ebanx.homeassigment.assignment.exceptions.AccountNotFoundException;
import com.ebanx.homeassigment.assignment.model.Account;
import com.ebanx.homeassigment.assignment.model.Event;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionService = new TransactionService();
    }

    @Test
    public void shouldResetAllAcounts() {
        Event event = new Event();
        event.setAmount(10);
        event.setDestination("100");
        transactionService.deposit(event);
        assertFalse(transactionService.getAllAcounts().isEmpty());

        transactionService.reset();
        assertTrue(transactionService.getAllAcounts().isEmpty());
    }

    @Test
    public void shouldAddAcountOnDeposit() {
        Optional<Account> account = transactionService.findAccount("100");
        assertFalse(account.isPresent());

        Event event = new Event();
        event.setAmount(10);
        event.setDestination("100");
        transactionService.deposit(event);

        account = transactionService.findAccount("100");
        assertTrue(account.isPresent());
        assertEquals("100", account.get().getId());
        assertEquals(10, account.get().getAmount());
    }

    @Test
    public void shouldAddToExistingAccount() {
        Optional<Account> account = transactionService.findAccount("100");
        assertFalse(account.isPresent());

        Event event = new Event();
        event.setAmount(10);
        event.setDestination("100");
        transactionService.deposit(event);
        transactionService.deposit(event);

        account = transactionService.findAccount("100");
        assertTrue(account.isPresent());
        assertEquals("100", account.get().getId());
        assertEquals(20, account.get().getAmount());
    }

    @Test
    public void shouldWithdrawExistingAccount() {
        Event event = new Event();
        event.setAmount(30);
        event.setDestination("100");
        transactionService.deposit(event);

        Optional<Account> account = transactionService.findAccount("100");
        assertTrue(account.isPresent());
        assertEquals("100", account.get().getId());
        assertEquals(30, account.get().getAmount());

        event.setDestination(null);
        event.setOrigin("100");
        event.setAmount(5);
        transactionService.withDraw(event);
        account = transactionService.findAccount("100");

        assertTrue(account.isPresent());
        assertEquals("100", account.get().getId());
        assertEquals(25, account.get().getAmount());
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowIfAccountIsNotFound() throws AccountNotFoundException {
        Event event = new Event();
        event.setDestination(null);
        event.setOrigin("100");
        event.setAmount(5);

        transactionService.withDraw(event);
    }

    @Test
    public void shouldReturnBalance() {
        Event event = new Event();
        event.setDestination("100");
        event.setAmount(15);

        transactionService.deposit(event);
        assertEquals(15, transactionService.getBalance("100"));
    }

    @Test
    public void shouldSetCorrectAmountAfterTransfer() {
        Event event = new Event();
        event.setDestination("100");
        event.setAmount(15);
        transactionService.deposit(event);

        event.setDestination("300");
        transactionService.deposit(event);

        event.setOrigin("100");
        event.setDestination("300");
        event.setAmount(5);
        transactionService.transfer(event);

        assertEquals(10, transactionService.getBalance("100"));
        assertEquals(20, transactionService.getBalance("300"));
    }

}