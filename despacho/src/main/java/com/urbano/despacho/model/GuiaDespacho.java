package com.urbano.despacho.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity @Table(name="guias_despacho") @Data @AllArgsConstructor @NoArgsConstructor
public class GuiaDespacho {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Long pedidoId;
    @Column(nullable=false) private String estado;
    @Column(nullable=false) private String direccionDestino;
    private LocalDate fechaEstimada;
    private String codigoSeguimiento;
}