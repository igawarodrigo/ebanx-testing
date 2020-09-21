package com.ebanx.homeassigment.assignment.model.response;


import com.ebanx.homeassigment.assignment.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositResponseTest {

    @Test
    public void shouldSerializeWithDestination() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Account account = new Account("100", 10);

        assertEquals("{\"destination\":{\"id\":\"100\",\"balance\":10}}",
                objectMapper.writeValueAsString(new DepositResponse(account)));

    }
}