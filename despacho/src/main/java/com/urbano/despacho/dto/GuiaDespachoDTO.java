package com.urbano.despacho.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
@Data @AllArgsConstructor @NoArgsConstructor
public class GuiaDespachoDTO {
    @NotNull private Long pedidoId;
    @NotBlank private String direccionDestino;
    private LocalDate fechaEstimada;
}
