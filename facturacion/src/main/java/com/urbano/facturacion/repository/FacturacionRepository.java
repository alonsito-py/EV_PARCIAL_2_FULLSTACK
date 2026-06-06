package com.urbano.facturacion.repository;
import com.urbano.facturacion.model.BoletaElectronica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface FacturacionRepository extends JpaRepository<BoletaElectronica, Long> {
    List<BoletaElectronica> findByPedidoId(Long pedidoId);
}