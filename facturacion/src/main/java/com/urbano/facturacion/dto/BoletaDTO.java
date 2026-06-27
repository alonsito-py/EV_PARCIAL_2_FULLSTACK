package com.urbano.facturacion.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
@Data @AllArgsConstructor @NoArgsConstructor
public class BoletaDTO {
    @NotNull private Long pedidoId;
    @NotBlank private String rutEmisor;
    @NotNull @DecimalMin("0.01") private BigDecimal montoNeto;
}