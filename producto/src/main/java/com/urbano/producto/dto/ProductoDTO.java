package com.urbano.producto.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
@Data @AllArgsConstructor @NoArgsConstructor
public class ProductoDTO {
    @NotBlank(message="El SKU es obligatorio") private String sku;
    @NotBlank(message="El nombre es obligatorio") private String nombre;
    @NotBlank(message="La descripción es obligatoria") private String descripcion;
    @NotNull(message="El precio es obligatorio") @DecimalMin(value="0.01", message="Precio debe ser mayor a 0") private BigDecimal precio;
    @NotBlank(message="La talla es obligatoria") private String talla;
    @NotBlank(message="El color es obligatorio") private String color;
    @NotNull(message="La categoría es obligatoria") private Long categoriaId;
}
