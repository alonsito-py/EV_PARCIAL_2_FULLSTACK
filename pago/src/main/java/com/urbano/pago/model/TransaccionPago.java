package com.urbano.pago.model;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Table(name="transacciones_pago") @Data @AllArgsConstructor @NoArgsConstructor
public class TransaccionPago {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private Long pedidoId;
    @Column(nullable=false) private BigDecimal monto;
    @Column(nullable=false) private String estadoPago; // PENDIENTE, APROBADO, RECHAZADO
    private String tokenWebpay;
    private LocalDateTime fechaPago = LocalDateTime.now();
}
