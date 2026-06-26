package com.banco.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "persona_id")
@Getter @Setter
public class Cliente extends Persona {
    
    @Column(name = "cliente_id", nullable = false, unique = true, length = 50)
    private String clienteId;
    
    @Column(nullable = false)
    private String contraseña;
    
    @Column(nullable = false)
    private Boolean estado;
}
