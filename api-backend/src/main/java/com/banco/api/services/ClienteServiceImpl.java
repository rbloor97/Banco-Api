package com.banco.api.services;

import com.banco.api.dtos.ClienteDTO;
import com.banco.api.entities.Cliente;
import com.banco.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Cliente guardar(ClienteDTO dto) {
        if (clienteRepository.existsById(dto.getIdentificacion())) {
            throw new RuntimeException("La identificacion ya se encuentra registrada");
        }
        if (clienteRepository.existsByClienteId(dto.getClienteId())) {
            throw new RuntimeException("El clienteId ya existe");
        }

        Cliente cliente = new Cliente();
        mapearDtoAEntidad(dto, cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente actualizar(Long id, ClienteDTO dto) {
        Cliente clienteExistente = obtenerPorId(id);
        mapearDtoAEntidad(dto, clienteExistente);
        return clienteRepository.save(clienteExistente);
    }

    @Override
    @Transactional
    public Cliente actualizarParcial(Long id, Map<String, Object> campos) {
        Cliente cliente = obtenerPorId(id);
        
        campos.forEach((llave, valor) -> {
            Field campo = ReflectionUtils.findField(Cliente.class, llave);
            if (campo == null) {
                campo = ReflectionUtils.findField(cliente.getClass().getSuperclass(), llave); 
            }
            
            if (campo != null) {
                campo.setAccessible(true);
                ReflectionUtils.setField(campo, cliente, valor);
            }
        });
        
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        cliente.setEstado(false);
        clienteRepository.save(cliente);
    }

    
    private void mapearDtoAEntidad(ClienteDTO dto, Cliente cliente) {
        cliente.setNombre(dto.getNombre());
        cliente.setGenero(dto.getGenero());
        cliente.setEdad(dto.getEdad());
        cliente.setIdentificacion(dto.getIdentificacion());
        cliente.setDireccion(dto.getDireccion());
        cliente.setTelefono(dto.getTelefono());
        cliente.setClienteId(dto.getClienteId());
        cliente.setContraseña(dto.getContraseña());
        cliente.setEstado(dto.getEstado());
    }
}
