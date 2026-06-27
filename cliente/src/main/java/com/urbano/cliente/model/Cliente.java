package com.urbano.cliente.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="clientes") @Data @AllArgsConstructor @NoArgsConstructor
public class Cliente {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private String nombre;
    @Column(nullable=false, unique=true) private String correo;
    @Column(nullable=false) private String telefono;
    private boolean verificado = false;
    private boolean activo = true;
}