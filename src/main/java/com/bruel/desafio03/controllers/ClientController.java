package com.bruel.desafio03.controllers;

import com.bruel.desafio03.dtos.ClientDTO;
import com.bruel.desafio03.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {
    @Autowired
    private ClientService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> findById(@PathVariable Long id){
        ClientDTO clientDTO = service.findById(id);
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping()
    public ResponseEntity<Page<ClientDTO>> findAll(Pageable pageable){
        Page<ClientDTO> clientDTO = service.findAll(pageable);
        return ResponseEntity.ok(clientDTO);
    }

    @PostMapping()
    public ResponseEntity<ClientDTO> insert(@RequestBody ClientDTO clientDto){
        clientDto = service.insert(clientDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(clientDto.getId())
                .toUri();

        return ResponseEntity.created(uri).body(clientDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO clientDto){
        clientDto = service.update(id, clientDto);
        return ResponseEntity.ok(clientDto);
    }
}
