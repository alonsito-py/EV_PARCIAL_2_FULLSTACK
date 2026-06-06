package com.urbano.promocion.repository;
import com.urbano.promocion.model.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface PromocionRepository extends JpaRepository<Promocion, Long> {
    List<Promocion> findByActivoTrue();
}