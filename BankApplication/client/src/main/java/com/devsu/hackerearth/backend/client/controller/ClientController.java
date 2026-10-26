package com.devsu.hackerearth.backend.client.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.service.ClientService;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

	private final ClientService clientService;

	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@SuppressWarnings("null")
	@GetMapping
	public ResponseEntity<List<ClientDto>> getAll() {
		return ResponseEntity.ok(clientService.getAll());
	}

	@SuppressWarnings("null")
	@GetMapping("/{id}")
	public ResponseEntity<ClientDto> get(@PathVariable Long id) {
		try{
			ClientDto client = clientService.getById(id);
			if(client != null){
				return ResponseEntity.ok(client);
			}else{
				return ResponseEntity.notFound().build();
			}
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
	}

	@SuppressWarnings("null")
	@PostMapping
	public ResponseEntity<ClientDto> create(@RequestBody ClientDto clientDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(clientService.create(clientDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClientDto> update(@PathVariable Long id,
			@RequestBody ClientDto clientDto) {
		try{
			
			clientDto.setId(id);
			ClientDto client = clientService.update(clientDto);
			if(client != null){
				return ResponseEntity.ok(client);
			}else{
				return ResponseEntity.notFound().build();
			}
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ClientDto> partialUpdate(@PathVariable Long id,
			@RequestBody PartialClientDto partialClientDto) {
		try{
			ClientDto client = clientService.partialUpdate(id,partialClientDto);
			if(client != null){
				return ResponseEntity.ok(client);
			}else{
				return ResponseEntity.notFound().build();
			}
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try{
			if(id != null){
				clientService.deleteById(id);
				return ResponseEntity.noContent().build();
			}else{
				return ResponseEntity.notFound().build(); 
			}
			
		}catch(Exception e){
			return ResponseEntity.notFound().build(); 
		}
	}
}
