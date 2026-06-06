package com.urbano.marketing.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
@Data @AllArgsConstructor @NoArgsConstructor
public class CampaniaDTO {
    @NotBlank private String nombre;
    @NotBlank private String red;
    @NotBlank private String contenido;
    private LocalDate fechaProgramada;
}