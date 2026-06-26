package com.banco.api.dtos;

import java.math.BigDecimal;
import lombok.*;

@Getter @Setter @Builder
public class DetalleReporteDTO {
    private String fecha;
    private String numeroCuenta;
    private String tipo;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;
}
