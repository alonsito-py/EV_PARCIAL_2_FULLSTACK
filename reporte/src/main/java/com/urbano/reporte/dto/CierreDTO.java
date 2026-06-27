package com.urbano.reporte.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data @AllArgsConstructor @NoArgsConstructor
public class CierreDTO {
    @NotNull private LocalDate fecha;
    @NotNull @Min(0) private Integer totalPedidos;
    @NotNull @DecimalMin("0.00") private BigDecimal montoTotal;
    private String observaciones;
}