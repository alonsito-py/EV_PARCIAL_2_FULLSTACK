package com.urbano.direccion.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="direcciones") @Data @AllArgsConstructor @NoArgsConstructor
public class Direccion {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Long clienteId;
    @Column(nullable=false) private String calle;
    @Column(nullable=false) private String comuna;
    @Column(nullable=false) private String region;
    private String referencias;
    private boolean principal = false;
}