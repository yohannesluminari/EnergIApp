package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.services.ClientService;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

//  @GetMapping
//  public ResponseEntity<Page<Client>> getAllClients(Pageable pageable) {
//      return ResponseEntity.ok(clientService.getAllClients(pageable));
//  }

//  @PostMapping
//  public ResponseEntity<Client> createClient(@RequestBody Client client) {
//      return ResponseEntity.status(HttpStatus.CREATED).body(clientService.saveClient(client));
//  }

    // Other methods for managing clients (PUT, DELETE, etc.)
}


