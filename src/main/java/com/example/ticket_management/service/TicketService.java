package com.example.ticket_management.service;

import com.example.ticket_management.dto.TicketCreateRequest;
import com.example.ticket_management.dto.TicketResponse;
import com.example.ticket_management.dto.TicketUpdateRequest;
import com.example.ticket_management.exception.TicketNotFoundException;
import com.example.ticket_management.model.Ticket;
import com.example.ticket_management.model.TicketStatus;
import com.example.ticket_management.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional(readOnly = true)
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(TicketResponse::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketResponse getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));
        return TicketResponse.fromEntity(ticket);
    }

    @Transactional
    public TicketResponse createTicket(TicketCreateRequest request) {
        Ticket ticket = Ticket.builder()
                .title(request.title())
                .description(request.description())
                .status(TicketStatus.OPEN) // Default initial state
                .priority(request.priority())
                .build();

        Ticket saved = ticketRepository.save(ticket);
        return TicketResponse.fromEntity(saved);
    }

    @Transactional
    public TicketResponse updateTicket(Long id, TicketUpdateRequest request) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException(id));

        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket.setStatus(request.status());
        ticket.setPriority(request.priority());

        return TicketResponse.fromEntity(ticket);
    }

    @Transactional
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException(id);
        }
        ticketRepository.deleteById(id);
    }
}
