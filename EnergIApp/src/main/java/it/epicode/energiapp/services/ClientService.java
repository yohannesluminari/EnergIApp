package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.repositories.ClientRepository;
import it.epicode.energiapp.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public List<Client> findCustomersByBusinessName(String name) {
        return clientRepository.findByCompanyNameContaining(name);
    }
}
