package com.urbano.autenticacion.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class UsuarioDTO {
    @NotBlank(message="El username es obligatorio") private String username;
    @NotBlank(message="El password es obligatorio") @Size(min=6, message="Mínimo 6 caracteres") private String password;
    @NotNull(message="El rol es obligatorio") private Long rolId;
}
