package com.banco.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClienteDTO {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El genero no puede estar vacio")
    private String genero;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "El cliente debe ser mayor de edad")
    private Integer edad;

    @NotBlank(message = "La identificacion es obligatoria")
    private String identificacion;

    private String direccion;
    private String telefono;

    @NotBlank(message = "El clienteId de negocio es obligatorio")
    private String clienteId;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 4, message = "La contraseña debe tener al menos 4 caracteres")
    private String contraseña;

    @NotNull(message = "El estado es obligatorio")
    private Boolean estado;
}

