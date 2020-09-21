package com.ebanx.homeassigment.assignment.model;

import com.ebanx.homeassigment.assignment.enums.Action;

public class Event {

    private Action type;

    private String origin;

    private String destination;

    private Integer amount;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Action getType() {
        return type;
    }

    public void setType(Action type) {
        this.type = type;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Event() {
    }
}
