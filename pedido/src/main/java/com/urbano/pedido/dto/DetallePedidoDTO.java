package com.urbano.pedido.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
@Data @AllArgsConstructor @NoArgsConstructor
public class DetallePedidoDTO {
    @NotBlank private String sku;
    @NotNull @Min(1) private Integer cantidad;
    @NotNull @DecimalMin("0.01") private BigDecimal precioUnitario;
}