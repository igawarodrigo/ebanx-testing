package com.ebanx.homeassigment.assignment.model.response;

import com.ebanx.homeassigment.assignment.model.Account;

public class WithdrawResponse {

    private Account origin;

    public WithdrawResponse() {

    }

    public WithdrawResponse(Account origin) {
        this.origin = origin;
    }

    public Account getOrigin() {
        return origin;
    }

    public void setOrigin(Account origin) {
        this.origin = origin;
    }
}
