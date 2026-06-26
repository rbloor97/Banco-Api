package com.banco.api.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.banco.api.entities.Movimiento;
import com.banco.api.dtos.*;

public interface MovimientoService {
    Movimiento registrarMovimiento(String numeroCuenta, String tipoMovimiento, BigDecimal valor);
    ReporteEstadoCuentaDTO generarReporte(String clienteId, LocalDate inicio, LocalDate fin);
}
