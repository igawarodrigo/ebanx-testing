package com.ebanx.homeassigment.assignment.controller;

import com.ebanx.homeassigment.assignment.model.request.EventDTO;
import com.ebanx.homeassigment.assignment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ebanx.homeassigment.assignment.enums.Action.DEPOSIT;
import static com.ebanx.homeassigment.assignment.enums.Action.WITHDRAW;
import static com.ebanx.homeassigment.assignment.utils.Paths.*;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping(RESET)
    public ResponseEntity<?> reset() {
        transactionService.reset();
        return ResponseEntity.ok("OK");
    }

    @GetMapping(BALANCE)
    public ResponseEntity<?> balance(@RequestParam("account_id") String accountId) {
        return ResponseEntity.ok(transactionService.getBalance(accountId));
    }

    @PostMapping(EVENT)
    public ResponseEntity<?> event(@RequestBody EventDTO event) {
        if (event.getType().equals(DEPOSIT)) {
            return ResponseEntity.created(null).body(transactionService.deposit(event));
        }
        if (event.getType().equals(WITHDRAW)) {
            return ResponseEntity.created(null).body(transactionService.withDraw(event));
        }
        return ResponseEntity.created(null).body(transactionService.transfer(event));
    }
}
