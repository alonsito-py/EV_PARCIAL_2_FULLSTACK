package com.urbano.cliente.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class ClienteDTO {
    @NotBlank(message="El nombre es obligatorio") private String nombre;
    @NotBlank(message="El correo es obligatorio") @Email(message="Correo invalido") private String correo;
    @NotBlank(message="El telefono es obligatorio") private String telefono;
}