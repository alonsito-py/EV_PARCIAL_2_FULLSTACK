package com.urbano.despacho.repository;
import com.urbano.despacho.model.GuiaDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface DespachoRepository extends JpaRepository<GuiaDespacho, Long> {
    List<GuiaDespacho> findByPedidoId(Long pedidoId);
    List<GuiaDespacho> findByEstado(String estado);
}
