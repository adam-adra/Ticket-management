package com.example.ticket_management.controller;

import com.example.ticket_management.dto.TicketCreateRequest;
import com.example.ticket_management.dto.TicketResponse;
import com.example.ticket_management.dto.TicketUpdateRequest;
import com.example.ticket_management.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        log.info("Fetching all tickets");
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        log.info("Fetching ticket with ID: {}", id);
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketCreateRequest request) {
        log.info("Received request to create ticket: {}", request.title());
        TicketResponse created = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponse> updateTicket(
            @PathVariable Long id,
            @Valid @RequestBody TicketUpdateRequest request
    ) {
        log.info("Received request to update ticket ID {}: {}", id, request.title());
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.info("Received request to delete ticket ID: {}", id);
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
