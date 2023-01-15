package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAllClients();
    Client findById(Long id);

    Client findByEmail(String email);
    void saveClient(Client client);
}
