package com.banco.api.repositories;

import com.banco.api.entities.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ClienteRepository extends JpaRepository<Cliente, Long>{
    Optional<Cliente> findByClienteId(String clienteId);
    boolean existsById(String identification);
    boolean existsByClienteId(String clientId);
    
}
