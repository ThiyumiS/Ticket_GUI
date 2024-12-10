package com.java.ticketing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.ticketing.service.TicketService;

@RestController
@RequestMapping("/api")
public class TicketController {
    @Autowired
    private TicketService service;

    @PostMapping("/simulate/vendor")
    public ResponseEntity<String> startVendor(@RequestParam int rate, @RequestParam String name) {
        service.startVendorSimulation(rate, name);
        return ResponseEntity.ok("Vendor simulation started");
    }

    @PostMapping("/simulate/customer")
    public ResponseEntity<String> startCustomer(@RequestParam String name) {
        service.startCustomerSimulation(name);
        return ResponseEntity.ok("Customer simulation started");
    }

    @PostMapping("/simulate/stop")
    public ResponseEntity<String> stopSimulation() {
        service.stopSimulation();
        return ResponseEntity.ok("Simulation stopped");
    }
}
