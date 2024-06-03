package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.ClientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    // GET all
    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }


    // GET id
    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundException("Client not found"));
    }

    public Page<Client> findClientsByBusinessName(String name, Pageable pageable) {
        return clientRepository.findByBusinessNameContaining(name, pageable);
    }

    public Page<Client> findClientsByAnnualRevenue(BigDecimal annualRevenue, Pageable pageable) {
        return clientRepository.findByAnnualRevenue(annualRevenue, pageable);
    }

    public Page<Client> findClientsByDateAdded(LocalDate dateAdded, Pageable pageable) {
        return clientRepository.findByDateAdded(dateAdded, pageable);
    }

    public Page<Client> findClientsByLastContactDate(LocalDate lastContactDate, Pageable pageable) {
        return clientRepository.findByLastContactDate(lastContactDate, pageable);
    }

    //PUT
    public Client updateClient(Long id, Client updatedClient) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client not found with id: " + id));

        // Copy updatedClient properties to existingClient, ignoring ID
        BeanUtils.copyProperties(updatedClient, existingClient, "id");

        return clientRepository.save(existingClient);
    }

    // POST
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }


    // DELETE
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}