package com.urbano.marketing.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
@Entity @Table(name="campanias_social") @Data @AllArgsConstructor @NoArgsConstructor
public class CampaniaSocial {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nombre;
    @Column(nullable=false) private String red;
    @Column(nullable=false) private String contenido;
    @Column(nullable=false) private String estado;
    private LocalDate fechaProgramada;
}