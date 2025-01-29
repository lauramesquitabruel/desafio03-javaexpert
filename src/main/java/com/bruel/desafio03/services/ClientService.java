package com.bruel.desafio03.services;

import com.bruel.desafio03.dtos.ClientDTO;
import com.bruel.desafio03.entities.Client;
import com.bruel.desafio03.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client result = (Client) repository.findById(id).get();
        return new ClientDTO(result);
    }
}
