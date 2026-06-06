package com.urbano.notificacion.dto;
import jakarta.validation.constraints.*;
import lombok.*;
@Data @AllArgsConstructor @NoArgsConstructor
public class NotificacionDTO {
    @NotBlank @Email private String destinatario;
    @NotBlank private String tipoPlantilla;
    private String mensaje;
}
