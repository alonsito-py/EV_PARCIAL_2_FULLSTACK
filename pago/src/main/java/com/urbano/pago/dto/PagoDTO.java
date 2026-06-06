package com.urbano.pago.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
@Data @AllArgsConstructor @NoArgsConstructor
public class PagoDTO {
    @NotNull private Long pedidoId;
    @NotNull @DecimalMin("0.01") private BigDecimal monto;
    @NotBlank private String tokenWebpay;
}
