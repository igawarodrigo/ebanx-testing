package com.ebanx.homeassigment.assignment.model.response;

import com.ebanx.homeassigment.assignment.model.Account;

public class DepositResponse {

    private Account destination;

    public DepositResponse() {

    }

    public DepositResponse(Account account) {
        this.destination = account;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }
}
