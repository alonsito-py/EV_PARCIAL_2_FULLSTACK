package com.urbano.notificacion.repository;
import com.urbano.notificacion.model.HistorialNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface NotificacionRepository extends JpaRepository<HistorialNotificacion, Long> {
    List<HistorialNotificacion> findByDestinatario(String destinatario);
    List<HistorialNotificacion> findByTipoPlantilla(String tipo);
}
