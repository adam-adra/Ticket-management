package com.example.ticket_management.dto;

import com.example.ticket_management.model.TicketPriority;
import com.example.ticket_management.model.TicketStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketUpdateRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must not exceed 100 characters")
    String title,

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    String description,

    @NotNull(message = "Status is required (OPEN, IN_PROGRESS, RESOLVED, CLOSED)")
    TicketStatus status,

    @NotNull(message = "Priority is required (LOW, MEDIUM, HIGH)")
    TicketPriority priority
) {}
