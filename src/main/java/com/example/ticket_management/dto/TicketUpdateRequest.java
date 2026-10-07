package com.example.ticket_management.dto;

import com.example.ticket_management.model.TicketPriority;
import com.example.ticket_management.model.TicketStatus;

public record TicketUpdateRequest(
    String title,
    String description,
    TicketStatus status,
    TicketPriority priority
) {}
