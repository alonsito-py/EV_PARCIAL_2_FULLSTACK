package com.urbano.notificacion.service;
import com.urbano.notificacion.dto.NotificacionDTO;
import com.urbano.notificacion.model.HistorialNotificacion;
import com.urbano.notificacion.repository.NotificacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional @Slf4j
public class NotificacionService {
    @Autowired private NotificacionRepository repo;
    public List<HistorialNotificacion> getAll() { return repo.findAll(); }
    public List<HistorialNotificacion> getByDestinatario(String dest) { return repo.findByDestinatario(dest); }
    public HistorialNotificacion enviar(NotificacionDTO dto) {
        log.info("Enviando notificacion tipo:{} a:{}", dto.getTipoPlantilla(), dto.getDestinatario());
        HistorialNotificacion h=new HistorialNotificacion();
        h.setDestinatario(dto.getDestinatario()); h.setTipoPlantilla(dto.getTipoPlantilla()); h.setMensaje(dto.getMensaje());
        // Simulación envío (en producción: JavaMail/SendGrid)
        h.setEstadoEnvio("ENVIADO");
        log.info("Notificacion enviada exitosamente a: {}", dto.getDestinatario());
        return repo.save(h);
    }
}
