package com.ebanx.homeassigment.assignment.model.response;

import com.ebanx.homeassigment.assignment.model.Account;

public class TransferResponse {

    private Account origin;

    private Account destination;

    public TransferResponse() {
    }

    public TransferResponse(Account origin, Account destination) {
        this.origin = origin;
        this.destination = destination;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }
}
