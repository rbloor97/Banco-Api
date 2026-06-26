package com.banco.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "cuentas")
@Getter @Setter
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta", nullable = false, unique = true, length = 20)
    private String numeroCuenta;

    @Column(name = "tipo_cuenta", nullable = false, length = 20)
    private String tipoCuenta;

    @Column(name = "saldo_inicial", nullable = false)
    private BigDecimal saldoInicial;

    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
