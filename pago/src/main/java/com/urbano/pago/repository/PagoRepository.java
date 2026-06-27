package com.urbano.pago.repository;
import com.urbano.pago.model.TransaccionPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PagoRepository extends JpaRepository<TransaccionPago, Long> {
    List<TransaccionPago> findByPedidoId(Long pedidoId);
}