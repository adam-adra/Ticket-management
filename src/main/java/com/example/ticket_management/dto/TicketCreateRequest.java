package com.example.ticket_management.dto;

import com.example.ticket_management.model.TicketPriority;

public record TicketCreateRequest(
    String title,
    String description,
    TicketPriority priority
) {}
