package com.urbano.bodega.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class MovimientoDTO {
    @NotBlank private String sku;
    @NotBlank private String tipo;
    @NotNull @Min(1) private Integer cantidad;
    private String motivo;
}
