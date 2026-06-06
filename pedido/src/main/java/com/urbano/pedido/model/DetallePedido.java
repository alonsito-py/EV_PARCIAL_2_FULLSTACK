package com.urbano.pedido.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Entity @Table(name="detalle_pedido") @Data @AllArgsConstructor @NoArgsConstructor
public class DetallePedido {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne @JoinColumn(name="pedido_id") @JsonIgnore private Pedido pedido;
    @Column(nullable=false) private String sku;
    @Column(nullable=false) private Integer cantidad;
    @Column(nullable=false) private BigDecimal precioUnitario;
}