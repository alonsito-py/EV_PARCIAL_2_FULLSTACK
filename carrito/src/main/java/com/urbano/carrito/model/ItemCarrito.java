package com.urbano.carrito.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Entity @Table(name="items_carrito") @Data @AllArgsConstructor @NoArgsConstructor
public class ItemCarrito {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="carrito_id") @JsonIgnore private Carrito carrito;
    @Column(nullable=false) private String sku;
    @Column(nullable=false) private Integer cantidad;
    @Column(nullable=false) private BigDecimal precioUnitario;
}
