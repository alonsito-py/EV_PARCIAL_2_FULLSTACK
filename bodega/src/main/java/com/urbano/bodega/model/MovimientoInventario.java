package com.urbano.bodega.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity @Table(name="movimientos_inventario") @Data @AllArgsConstructor @NoArgsConstructor
public class MovimientoInventario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String sku;
    @Column(nullable=false) private String tipo;
    @Column(nullable=false) private Integer cantidad;
    private String motivo;
    private LocalDateTime fecha = LocalDateTime.now();
}
