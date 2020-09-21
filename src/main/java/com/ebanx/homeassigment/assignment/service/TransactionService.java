package com.ebanx.homeassigment.assignment.service;

import com.ebanx.homeassigment.assignment.exceptions.AccountNotFoundException;
import com.ebanx.homeassigment.assignment.model.Account;
import com.ebanx.homeassigment.assignment.model.request.EventDTO;
import com.ebanx.homeassigment.assignment.model.response.DepositResponse;
import com.ebanx.homeassigment.assignment.model.response.TransferResponse;
import com.ebanx.homeassigment.assignment.model.response.WithdrawResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionService {

    private HashMap<String, Account> allAcounts = new HashMap<>();

    public void reset() {
        allAcounts.clear();
    }

    public DepositResponse deposit(EventDTO event) {
        Account account = findAccount(event.getDestination())
                .map( acc -> {
                    acc.setAmount(acc.getAmount() + event.getAmount());
                    return acc;
                })
                .orElse(new Account(event.getDestination(), event.getAmount()));

        allAcounts.put(event.getDestination(), account);

        return new DepositResponse(account);
    }

    Optional<Account> findAccount(String accountId) {
        return allAcounts.entrySet()
                .stream()
                .filter( entry -> entry.getKey().equals(accountId))
                .findFirst()
                .map(Map.Entry::getValue);
    }

    public TransferResponse transfer(EventDTO event) {
        Account origin = findAccount(event.getOrigin())
                .orElseThrow(AccountNotFoundException::new);
        Account destination = findAccount(event.getDestination())
                .orElseGet(() -> new Account(event.getDestination(), 0));

        origin.setAmount(origin.getAmount() - event.getAmount());
        destination.setAmount(event.getAmount() + destination.getAmount());
        allAcounts.put(destination.getId(), destination);

        return new TransferResponse(origin, destination);
    }

    public WithdrawResponse withDraw(EventDTO event) {
        Account account = findAccount(event.getOrigin())
                .map( acc -> {
                    acc.setAmount(acc.getAmount() - event.getAmount());
                    return acc;
                })
                .orElseThrow(AccountNotFoundException::new);

        return new WithdrawResponse(account);
    }

    public Integer getBalance(String accountId) {
        return findAccount(accountId)
                .map(Account::getAmount)
                .orElseThrow(AccountNotFoundException::new);
    }

    HashMap<String, Account> getAllAcounts(){
        return allAcounts;
    }

}
