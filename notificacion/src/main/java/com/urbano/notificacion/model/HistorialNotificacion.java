package com.urbano.notificacion.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="historial_notificaciones") @Data @AllArgsConstructor @NoArgsConstructor
public class HistorialNotificacion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String destinatario;
    @Column(nullable=false) private String tipoPlantilla;
    @Column(nullable=false) private String estadoEnvio;
    private String mensaje;
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}