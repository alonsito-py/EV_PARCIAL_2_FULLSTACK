package com.urbano.promocion.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
@Data @AllArgsConstructor @NoArgsConstructor
public class CuponDTO {
    @NotBlank private String codigo;
    @NotNull @DecimalMin("0.01") private BigDecimal descuento;
    @NotNull @Min(1) private Integer usosMaximos;
}