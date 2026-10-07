package com.example.ticket_management.dto;

import com.example.ticket_management.model.Ticket;
import com.example.ticket_management.model.TicketPriority;
import com.example.ticket_management.model.TicketStatus;

import java.time.LocalDateTime;

public record TicketResponse(
    Long id,
    String title,
    String description,
    TicketStatus status,
    TicketPriority priority,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static TicketResponse fromEntity(Ticket ticket) {
        return new TicketResponse(
            ticket.getId(),
            ticket.getTitle(),
            ticket.getDescription(),
            ticket.getStatus(),
            ticket.getPriority(),
            ticket.getCreatedAt(),
            ticket.getUpdatedAt()
        );
    }
}
