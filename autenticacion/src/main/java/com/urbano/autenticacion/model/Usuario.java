package com.urbano.autenticacion.model;
import jakarta.persistence.*;
import lombok.*;
@Entity @Table(name="usuarios") @Data @AllArgsConstructor @NoArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String username;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private boolean activo = true;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="rol_id", nullable=false)
    private Rol rol;
}
