package com.ebanx.homeassigment.assignment.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AccountTest {

    @Test
    public void shouldDeserialise() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Account account = objectMapper.readValue("{\"id\":\"100\",\"balance\":10}" ,Account.class);

        assertEquals(Integer.valueOf(10), account.getAmount());
        assertEquals("100", account.getId());
    }

}