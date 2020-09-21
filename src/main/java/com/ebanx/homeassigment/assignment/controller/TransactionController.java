package com.ebanx.homeassigment.assignment.controller;

import com.ebanx.homeassigment.assignment.model.Event;
import com.ebanx.homeassigment.assignment.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ebanx.homeassigment.assignment.enums.Action.DEPOSIT;
import static com.ebanx.homeassigment.assignment.enums.Action.WITHDRAW;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/reset")
    public ResponseEntity<?> reset() {
        transactionService.reset();
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/balance")
    public ResponseEntity<?> balance(@RequestParam("account_id") String accountId) {
        return ResponseEntity.ok(transactionService.getBalance(accountId));
    }

    @PostMapping("/event")
    public ResponseEntity<?> event(@RequestBody Event event) {
        if (event.getType().equals(DEPOSIT)) {
            return ResponseEntity.created(null).body(transactionService.deposit(event));
        }
        if (event.getType().equals(WITHDRAW)) {
            return ResponseEntity.created(null).body(transactionService.withDraw(event));
        }
        return ResponseEntity.created(null).body(transactionService.transfer(event));
    }
}
