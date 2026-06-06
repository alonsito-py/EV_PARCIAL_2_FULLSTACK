package com.urbano.inventario.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="inventario") @Data @AllArgsConstructor @NoArgsConstructor
public class Inventario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String sku;
    @Column(nullable=false) private Integer cantidadDisponible;
    @Column(nullable=false) private Integer alertaMinimo;
}
