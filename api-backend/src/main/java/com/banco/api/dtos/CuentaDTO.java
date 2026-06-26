package com.banco.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class CuentaDTO {

    @NotBlank(message = "El numero de cuenta no puede estar vacio")
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta es obligatorio (Ahorro/Corriente)")
    private String tipoCuenta;

    @NotNull(message = "El saldo inicial es obligatorio")
    @PositiveOrZero(message = "El saldo inicial no puede ser negativo")
    private BigDecimal saldoInicial;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;

    @NotBlank(message = "El clienteId de negocio es obligatorio para asociar la cuenta")
    private String clienteId;
}
