package com.example.ticket_management.exception;

public class TicketNotFoundException extends RuntimeException {
    public TicketNotFoundException(Long id){
        super("Ticket not found with id: " + id);
    }
}
