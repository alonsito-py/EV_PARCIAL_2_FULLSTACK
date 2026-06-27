package com.urbano.producto.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Entity @Table(name="productos") @Data @AllArgsConstructor @NoArgsConstructor
public class Producto {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String sku;
    @Column(nullable=false) private String nombre;
    @Column(nullable=false) private String descripcion;
    @Column(nullable=false) private BigDecimal precio;
    @Column(nullable=false) private String talla;
    @Column(nullable=false) private String color;
    private boolean activo = true;
    @ManyToOne(fetch=FetchType.EAGER) @JoinColumn(name="categoria_id", nullable=false)
    private Categoria categoria;
}