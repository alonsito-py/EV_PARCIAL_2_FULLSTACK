package com.urbano.cliente.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class ClienteDTO {
    @NotBlank(message="El nombre es obligatorio") private String nombre;
    @NotBlank(message="El correo es obligatorio") @Email(message="Correo inválido") private String correo;
    @NotBlank(message="El teléfono es obligatorio") private String telefono;
}
