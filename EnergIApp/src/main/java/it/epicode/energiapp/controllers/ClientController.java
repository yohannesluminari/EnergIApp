package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.Address;
import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.exceptions.NoContentException;
import it.epicode.energiapp.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // GET http://localhost:8080/api/clients

    @GetMapping
    public ResponseEntity<Page<Client>> getAllEvents(Pageable pageable) {
        Page<Client> clients = clientService.findAllClients(pageable);
        if (clients.isEmpty()) {
            throw new NoContentException("No clients were found.");
        } else {
            ResponseEntity<Page<Client>> responseEntity = new ResponseEntity<>(clients, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/clients/{id}

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.findClientById(id);
        return ResponseEntity.ok(client);
    }

    // POST http://localhost:8080/api/clients

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client createdClient = clientService.saveClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }

    // PUT http://localhost:8080/api/clients/{id}

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        Client client = clientService.updateClient(id, updatedClient);
        return ResponseEntity.ok(client);
    }

    // DELETE http://localhost:8080/api/clients/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client with id: " + id + " has been deleted successfully");
    }
}
