package com.ebanx.homeassigment.assignment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {

    private String id;

    @JsonProperty("balance")
    private Integer amount;

    public Account() {

    }

    public Account(String id) {
        this.id = id;
    }

    public Account(String id, Integer amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
