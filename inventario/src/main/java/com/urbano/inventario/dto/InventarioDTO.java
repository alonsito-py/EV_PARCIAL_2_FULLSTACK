package com.urbano.inventario.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class InventarioDTO {
    @NotBlank(message="El SKU es obligatorio") private String sku;
    @NotNull @Min(0) private Integer cantidadDisponible;
    @NotNull @Min(0) private Integer alertaMinimo;
}