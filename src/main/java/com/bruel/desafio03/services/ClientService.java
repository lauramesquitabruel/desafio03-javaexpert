package com.bruel.desafio03.services;

import com.bruel.desafio03.dtos.ClientDTO;
import com.bruel.desafio03.entities.Client;
import com.bruel.desafio03.repositories.ClientRepository;
import com.bruel.desafio03.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository repository;

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){
        Client result = (Client) repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado")
        );

        return new ClientDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable){
        Page<Client> result = repository.findAll(pageable);
        return result.map(ClientDTO::new);
    }

    @Transactional
    public ClientDTO insert(ClientDTO clientDto){
        Client client = new Client();
        copyDtoToEntity(clientDto, client);

        client = repository.save(client);

        return new ClientDTO(client);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO clientDto){
        try{
            Client client = repository.getReferenceById(id);
            copyDtoToEntity(clientDto, client);

            client = repository.save(client);

            return new ClientDTO(client);
        } catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }

    }

    @Transactional
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        repository.deleteById(id);
    }

    private void copyDtoToEntity(ClientDTO clientDto, Client entity){
        entity.setName(clientDto.getName());
        entity.setCpf(clientDto.getCpf());
        entity.setIncome(clientDto.getIncome());
        entity.setBirthDate(clientDto.getBirthDate());
        entity.setChildren(clientDto.getChildren());
    }
}
