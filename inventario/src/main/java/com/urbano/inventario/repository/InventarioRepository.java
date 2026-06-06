package com.urbano.inventario.repository;
import com.urbano.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    Optional<Inventario> findBySku(String sku);
    List<Inventario> findByCantidadDisponibleLessThanEqualAndAlertaMinimo(Integer cantidad, Integer alerta);
}
