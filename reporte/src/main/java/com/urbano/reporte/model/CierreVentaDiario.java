package com.urbano.reporte.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity @Table(name="cierre_venta_diario") @Data @AllArgsConstructor @NoArgsConstructor
public class CierreVentaDiario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private LocalDate fecha;
    @Column(nullable=false) private Integer totalPedidos;
    @Column(nullable=false) private BigDecimal montoTotal;
    private String observaciones;
}