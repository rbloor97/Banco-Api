package com.banco.api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter

public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(nullable = false, length = 20)
    private String genero;
    
    @Column(nullable = false)
    private Integer edad;
    
    @Column(nullable = false, unique = true, length = 20)
    private String identificacion;
    
    @Column(length = 200)
    private String direccion;
    
    @Column(length = 20)
    private String telefono;    
}
