package com.ebanx.homeassigment.assignment.model.response;


import com.ebanx.homeassigment.assignment.model.Account;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class TransferResponseTest {

    @Test
    public void shouldSerializeWithOrignAndDestination() throws JsonProcessingException {
        Account origin = new Account("100", 10);
        Account destinaton = new Account("300", 15);
        ObjectMapper objectMapper = new ObjectMapper();

        assertEquals("{\"origin\":{\"id\":\"100\",\"balance\":10},\"destination\":{\"id\":\"300\",\"balance\":15}}",
                objectMapper.writeValueAsString(new TransferResponse(origin, destinaton)));

    }

}