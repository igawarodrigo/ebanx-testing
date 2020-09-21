package com.ebanx.homeassigment.assignment.model;

import com.ebanx.homeassigment.assignment.enums.Action;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static com.ebanx.homeassigment.assignment.enums.Action.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EventTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldDeserializeDeposit() throws JsonProcessingException {
        Event event = objectMapper.readValue("{\"type\":\"deposit\", \"destination\":\"100\", \"amount\":10}", Event.class);

        assertEquals(Integer.valueOf(10), event.getAmount());
        assertEquals("100", event.getDestination());
        assertEquals(DEPOSIT, event.getType());
        assertNull(event.getOrigin());
    }

    @Test
    public void shouldDeserializeWithDraw() throws JsonProcessingException {
        Event event = objectMapper.readValue("{\"type\":\"withdraw\", \"origin\":\"100\", \"amount\":10}", Event.class);

        assertEquals(Integer.valueOf(10), event.getAmount());
        assertEquals("100", event.getOrigin());
        assertEquals(WITHDRAW, event.getType());
        assertNull(event.getDestination());
    }

    @Test
    public void shouldDeserializeTransfer() throws JsonProcessingException {
        Event event = objectMapper.readValue("{\"type\":\"transfer\", \"origin\":\"100\", \"amount\":15, \"destination\":\"300\"}", Event.class);

        assertEquals(Integer.valueOf(15), event.getAmount());
        assertEquals("100", event.getOrigin());
        assertEquals("300", event.getDestination());
        assertEquals(TRANSFER, event.getType());
    }

}