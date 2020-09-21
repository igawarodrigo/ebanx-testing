package com.ebanx.homeassigment.assignment.service;

import com.ebanx.homeassigment.assignment.exceptions.AccountNotFoundException;
import com.ebanx.homeassigment.assignment.model.Account;
import com.ebanx.homeassigment.assignment.model.request.EventDTO;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.ebanx.homeassigment.assignment.enums.Action.DEPOSIT;
import static com.ebanx.homeassigment.assignment.enums.Action.WITHDRAW;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionServiceTest {

    public static final String ACCOUNT_ID_100 = "100";
    public static final String ACCOUNT_ID_300 = "300";

    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionService = new TransactionService();
    }

    @Test
    public void shouldResetAllAcounts() {
        transactionService.deposit(new EventDTO(DEPOSIT, null, ACCOUNT_ID_100, 10));
        assertFalse(transactionService.getAllAcounts().isEmpty());

        transactionService.reset();
        assertTrue(transactionService.getAllAcounts().isEmpty());
    }

    @Test
    public void shouldAddAcountOnDeposit() {
        Optional<Account> account = transactionService.findAccount(ACCOUNT_ID_100);
        assertFalse(account.isPresent());

        transactionService.deposit(new EventDTO(DEPOSIT, null, ACCOUNT_ID_100, 10));
        account = transactionService.findAccount(ACCOUNT_ID_100);

        assertTrue(account.isPresent());
        assertEquals(ACCOUNT_ID_100, account.get().getId());
        assertEquals(10, account.get().getAmount());
    }

    @Test
    public void shouldAddToExistingAccount() {
        Optional<Account> account = transactionService.findAccount(ACCOUNT_ID_100);
        assertFalse(account.isPresent());

        EventDTO event = new EventDTO(DEPOSIT, null, ACCOUNT_ID_100, 10);
        transactionService.deposit(event);
        transactionService.deposit(event);
        account = transactionService.findAccount(ACCOUNT_ID_100);

        assertTrue(account.isPresent());
        assertEquals(ACCOUNT_ID_100, account.get().getId());
        assertEquals(20, account.get().getAmount());
    }

    @Test
    public void shouldWithdrawExistingAccount() {
        EventDTO event = new EventDTO();
        event.setAmount(30);
        event.setDestination(ACCOUNT_ID_100);
        transactionService.deposit(event);

        Optional<Account> account = transactionService.findAccount(ACCOUNT_ID_100);
        assertTrue(account.isPresent());
        assertEquals(ACCOUNT_ID_100, account.get().getId());
        assertEquals(30, account.get().getAmount());

        event.setDestination(null);
        event.setOrigin(ACCOUNT_ID_100);
        event.setAmount(5);
        transactionService.withDraw(event);
        account = transactionService.findAccount(ACCOUNT_ID_100);

        assertTrue(account.isPresent());
        assertEquals(ACCOUNT_ID_100, account.get().getId());
        assertEquals(25, account.get().getAmount());
    }

    @Test(expected = AccountNotFoundException.class)
    public void shouldThrowIfAccountIsNotFound() throws AccountNotFoundException {
        transactionService.withDraw(new EventDTO(WITHDRAW, ACCOUNT_ID_100, null, 5));
    }

    @Test
    public void shouldReturnBalance() {
        transactionService.deposit(new EventDTO(DEPOSIT, null, ACCOUNT_ID_100, 15));

        assertEquals(15, transactionService.getBalance(ACCOUNT_ID_100));
    }

    @Test
    public void shouldSetCorrectAmountAfterTransfer() {
        EventDTO event = new EventDTO();
        event.setDestination(ACCOUNT_ID_100);
        event.setAmount(15);
        transactionService.deposit(event);

        event.setDestination(ACCOUNT_ID_300);
        transactionService.deposit(event);

        event.setOrigin(ACCOUNT_ID_100);
        event.setDestination(ACCOUNT_ID_300);
        event.setAmount(5);
        transactionService.transfer(event);

        assertEquals(10, transactionService.getBalance(ACCOUNT_ID_100));
        assertEquals(20, transactionService.getBalance(ACCOUNT_ID_300));
    }
}