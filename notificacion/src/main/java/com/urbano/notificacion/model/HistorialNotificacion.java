package com.urbano.notificacion.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="historial_notificaciones") @Data @AllArgsConstructor @NoArgsConstructor
public class HistorialNotificacion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String destinatario;
    @Column(nullable=false) private String tipoPlantilla; // REGISTRO, COMPRA_APROBADA, DESPACHO
    @Column(nullable=false) private String estadoEnvio; // ENVIADO, FALLIDO
    private String mensaje;
    private LocalDateTime fechaEnvio = LocalDateTime.now();
}
