package com.urbano.facturacion.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name="boletas_electronicas") @Data @AllArgsConstructor @NoArgsConstructor
public class BoletaElectronica {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Long pedidoId;
    @Column(nullable=false, unique=true) private String folio;
    @Column(nullable=false) private String rutEmisor;
    @Column(nullable=false) private BigDecimal montoNeto;
    @Column(nullable=false) private BigDecimal iva;
    @Column(nullable=false) private BigDecimal montoTotal;
    private LocalDateTime fechaEmision = LocalDateTime.now();
}