package com.urbano.direccion.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class DireccionDTO {
    @NotNull(message="El clienteId es obligatorio") private Long clienteId;
    @NotBlank(message="La calle es obligatoria") private String calle;
    @NotBlank(message="La comuna es obligatoria") private String comuna;
    @NotBlank(message="La region es obligatoria") private String region;
    private String referencias;
    private boolean principal;
}