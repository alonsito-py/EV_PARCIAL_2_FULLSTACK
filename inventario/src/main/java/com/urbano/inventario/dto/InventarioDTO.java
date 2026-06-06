package com.urbano.inventario.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class InventarioDTO {
    @NotBlank(message="El SKU es obligatorio") private String sku;
    @NotNull(message="La cantidad es obligatoria") @Min(value=0) private Integer cantidadDisponible;
    @NotNull(message="La alerta mínima es obligatoria") @Min(value=0) private Integer alertaMinimo;
}
