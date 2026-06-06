package com.urbano.promocion.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data @AllArgsConstructor @NoArgsConstructor
public class PromocionDTO {
    @NotBlank private String nombre;
    @NotNull @DecimalMin("0.01") @DecimalMax("100.00") private BigDecimal descuento;
    @NotNull private LocalDate fechaInicio;
    @NotNull private LocalDate fechaFin;
}