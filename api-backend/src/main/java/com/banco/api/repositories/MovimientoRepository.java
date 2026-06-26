package com.banco.api.repositories;

import com.banco.api.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    
    @Query("SELECT COALESCE(SUM(ABS(m.valor)), 0) FROM Movimiento m " +
           "WHERE m.cuenta.id = :cuentaId " +
           "AND m.tipoMovimiento = 'Debito' " +
           "AND m.fecha >= :inicioDia AND m.fecha <= :finDia")
    BigDecimal sumDebitosDelDia(@Param("cuentaId") Long cuentaId, 
                                @Param("inicioDia") LocalDateTime inicioDia, 
                                @Param("finDia") LocalDateTime finDia);

    
    List<Movimiento> findByCuentaClienteIdAndFechaBetween(Long clienteId, LocalDateTime inicio, LocalDateTime fin);
}

