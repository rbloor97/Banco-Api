package com.banco.api.services;

import java.util.List;
import java.util.Map;

import com.banco.api.dtos.ClienteDTO;
import com.banco.api.entities.Cliente;

public interface ClienteService {
    List<Cliente> listarTodos();
    Cliente obtenerPorId(Long id);
    Cliente guardar(ClienteDTO clienteDTO);
    Cliente actualizar(Long id, ClienteDTO clienteDTO);
    Cliente actualizarParcial(Long id, Map<String, Object> campos);
    void eliminar(Long id);
}
