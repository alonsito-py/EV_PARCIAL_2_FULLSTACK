package com.urbano.promocion.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Entity @Table(name="cupones") @Data @AllArgsConstructor @NoArgsConstructor
public class Cupon {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String codigo;
    @Column(nullable=false) private BigDecimal descuento;
    @Column(nullable=false) private Integer usosMaximos;
    private Integer usosActuales = 0;
    private boolean activo = true;
}
