package com.urbano.carrito.repository;
import com.urbano.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByClienteIdAndEstado(Long clienteId, String estado);
}