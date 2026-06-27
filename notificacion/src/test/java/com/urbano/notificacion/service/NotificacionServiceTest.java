package com.urbano.notificacion.service;

import com.urbano.notificacion.dto.NotificacionDTO;
import com.urbano.notificacion.model.HistorialNotificacion;
import com.urbano.notificacion.repository.NotificacionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - NotificacionService")
class NotificacionServiceTest {

    @Mock private NotificacionRepository notificacionRepository;
    @InjectMocks private NotificacionService notificacionService;

    private HistorialNotificacion notificacion;

    @BeforeEach
    void setUp() {
        notificacion = new HistorialNotificacion();
        notificacion.setId(1L);
        notificacion.setDestinatario("cliente@urbano.cl");
        notificacion.setTipoPlantilla("BIENVENIDA");
        notificacion.setEstadoEnvio("ENVIADO");
        notificacion.setMensaje("Bienvenido a Urbano");
    }

    @Test
    @DisplayName("getAll - debería retornar todo el historial de notificaciones")
    void testGetAll() {
        when(notificacionRepository.findAll()).thenReturn(List.of(notificacion));
        List<HistorialNotificacion> result = notificacionService.getAll();
        assertEquals(1, result.size());
        verify(notificacionRepository).findAll();
    }

    @Test
    @DisplayName("getByDestinatario - debería retornar notificaciones del destinatario")
    void testGetByDestinatario() {
        when(notificacionRepository.findByDestinatario("cliente@urbano.cl")).thenReturn(List.of(notificacion));
        List<HistorialNotificacion> result = notificacionService.getByDestinatario("cliente@urbano.cl");
        assertFalse(result.isEmpty());
        assertEquals("ENVIADO", result.get(0).getEstadoEnvio());
    }

    @Test
    @DisplayName("enviar - debería guardar y retornar la notificación")
    void testEnviar() {
        NotificacionDTO dto = new NotificacionDTO("cliente@urbano.cl", "BIENVENIDA", "Bienvenido");
        when(notificacionRepository.save(any())).thenReturn(notificacion);
        HistorialNotificacion result = notificacionService.enviar(dto);
        assertNotNull(result);
        verify(notificacionRepository).save(any(HistorialNotificacion.class));
    }
}
