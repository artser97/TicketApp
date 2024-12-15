package com.example.TicketService.controller;

import com.example.TicketService.model.Ticket;
import com.example.TicketService.repository.TicketRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

  private final TicketRepository repository;

  public TicketController(TicketRepository repository) {
    this.repository = repository;
  }

  @GetMapping
  public List<Ticket> getAllTickets() {
    return repository.findAll();
  }

  @PostMapping
  public Ticket createTicket(@RequestBody Ticket ticket) {
    return repository.save(ticket);
  }
}
