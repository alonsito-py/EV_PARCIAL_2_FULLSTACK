package com.urbano.carrito.model;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
@Entity @Table(name="carritos") @Data @AllArgsConstructor @NoArgsConstructor
public class Carrito {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Long clienteId;
    @Column(nullable=false) private String estado;
    @OneToMany(mappedBy="carrito", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<ItemCarrito> items = new ArrayList<>();
}