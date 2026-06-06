package com.urbano.promocion.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
@Entity @Table(name="promociones") @Data @AllArgsConstructor @NoArgsConstructor
public class Promocion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nombre;
    @Column(nullable=false) private BigDecimal descuento; // porcentaje 0-100
    @Column(nullable=false) private LocalDate fechaInicio;
    @Column(nullable=false) private LocalDate fechaFin;
    private boolean activo = true;
}
