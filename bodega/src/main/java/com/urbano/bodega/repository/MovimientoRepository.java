package com.urbano.bodega.repository;
import com.urbano.bodega.model.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoInventario, Long> {
    List<MovimientoInventario> findBySku(String sku);
    List<MovimientoInventario> findByTipo(String tipo);
}