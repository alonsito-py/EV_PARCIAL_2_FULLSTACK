package com.urbano.pedido.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;
@Data @AllArgsConstructor @NoArgsConstructor
public class PedidoDTO {
    @NotNull private Long clienteId;
    @NotNull private Long direccionId;
    @NotEmpty private List<DetallePedidoDTO> detalles;
}
