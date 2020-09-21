package com.ebanx.homeassigment.assignment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum Action {
    DEPOSIT("deposit"),
    WITHDRAW("withdraw"),
    TRANSFER("transfer");

    private String action;

    public String getAction() {
        return this.action;
    }

    @Override
    public String toString(){
        return this.action;
    }

    Action(String action) {
        this.action = action;
    }

    @JsonCreator
    public static Action forValues(String action) {
        return Arrays.stream(Action.values())
                .filter( ac -> ac.getAction().equalsIgnoreCase(action))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
