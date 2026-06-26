package com.banco.api.repositories;

import com.banco.api.entities.Cuenta;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CuentaRepository extends JpaRepository<Cuenta, Long>{
    Optional<Cuenta> findByNumeroCuenta(String numeroCuenta);
}
