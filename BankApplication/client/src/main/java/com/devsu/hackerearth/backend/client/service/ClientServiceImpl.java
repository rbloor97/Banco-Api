package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll().stream().map(client -> new ClientDto(
				client.getId(),
				client.getDni(),
				client.getName(),
				client.getPassword(),
				client.getGender(),
				client.getAge(),
				client.getAddress(),
				client.getPhone(),
				client.isActive())).collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		@SuppressWarnings("null")
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Client not found"));
		return new ClientDto(
				client.getId(),
				client.getDni(),
				client.getName(),
				client.getPassword(),
				client.getGender(),
				client.getAge(),
				client.getAddress(),
				client.getPhone(),
				client.isActive());
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = new Client();
		client.setName(clientDto.getName());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setDni(clientDto.getDni());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setPassword(clientDto.getPassword());
		client.setActive(clientDto.isActive());

		Client saved = clientRepository.save(client);
		return new ClientDto(
				saved.getId(),
				saved.getDni(),
				saved.getName(),
				saved.getPassword(),
				saved.getGender(),
				saved.getAge(),
				saved.getAddress(),
				saved.getPhone(),
				saved.isActive());
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		@SuppressWarnings("null")
		Client client = clientRepository.findById(clientDto.getId())
				.orElseThrow(() -> new RuntimeException("Client not found"));
		client.setName(clientDto.getName());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setDni(clientDto.getDni());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setPassword(clientDto.getPassword());
		client.setActive(clientDto.isActive());

		Client update = clientRepository.save(client);
		return new ClientDto(
				update.getId(),
				update.getDni(),
				update.getName(),
				update.getPassword(),
				update.getGender(),
				update.getAge(),
				update.getAddress(),
				update.getPhone(),
				update.isActive());
	}

	@SuppressWarnings("null")
	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Client not found"));
		if (partialClientDto != null) {
			client.setActive(partialClientDto.isActive());
		}
		Client updated = clientRepository.save(client);
		return new ClientDto(
				updated.getId(),
				updated.getDni(),
				updated.getName(),
				updated.getPassword(),
				updated.getGender(),
				updated.getAge(),
				updated.getAddress(),
				updated.getPhone(),
				updated.isActive());
	}

	@SuppressWarnings("null")
	@Override
	public void deleteById(Long id) {
		clientRepository.deleteById(id);
	}
}
