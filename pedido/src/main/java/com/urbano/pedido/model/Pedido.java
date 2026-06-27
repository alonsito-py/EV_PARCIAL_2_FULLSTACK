package com.urbano.pedido.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity @Table(name="pedidos") @Data @AllArgsConstructor @NoArgsConstructor
public class Pedido {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Long clienteId;
    @Column(nullable=false) private Long direccionId;
    @Column(nullable=false) private String estado;
    @Column(nullable=false) private BigDecimal total;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @OneToMany(mappedBy="pedido", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<DetallePedido> detalles = new ArrayList<>();
}