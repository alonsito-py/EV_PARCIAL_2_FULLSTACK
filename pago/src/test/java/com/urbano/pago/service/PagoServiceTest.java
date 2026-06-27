package com.urbano.pago.service;

import com.urbano.pago.dto.PagoDTO;
import com.urbano.pago.model.TransaccionPago;
import com.urbano.pago.repository.PagoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - PagoService")
class PagoServiceTest {

    @Mock private PagoRepository pagoRepository;
    @Mock private WebClient webClientPedido;
    @InjectMocks private PagoService pagoService;

    private TransaccionPago transaccion;

    @BeforeEach
    void setUp() {
        transaccion = new TransaccionPago();
        transaccion.setId(1L);
        transaccion.setPedidoId(10L);
        transaccion.setMonto(new BigDecimal("59500"));
        transaccion.setEstadoPago("APROBADO");
        transaccion.setTokenWebpay("TOKEN-VALID-123");
    }

    @Test
    @DisplayName("getByPedido - debería retornar transacciones del pedido")
    void testGetByPedido() {
        when(pagoRepository.findByPedidoId(10L)).thenReturn(List.of(transaccion));
        List<TransaccionPago> result = pagoService.getByPedido(10L);
        assertEquals(1, result.size());
        assertEquals("APROBADO", result.get(0).getEstadoPago());
    }

    @Test
    @DisplayName("procesar - debería aprobar pago con token válido")
    void testProcesar_aprobado() {
        PagoDTO dto = new PagoDTO(10L, new BigDecimal("59500"), "TOKEN-VALID-123");
        when(pagoRepository.save(any())).thenReturn(transaccion);
        TransaccionPago result = pagoService.procesar(dto);
        assertNotNull(result);
        verify(pagoRepository).save(any(TransaccionPago.class));
    }

    @Test
    @DisplayName("procesar - debería rechazar pago con token nulo")
    void testProcesar_rechazado() {
        PagoDTO dto = new PagoDTO(10L, new BigDecimal("59500"), null);
        transaccion.setEstadoPago("RECHAZADO");
        when(pagoRepository.save(any())).thenAnswer(inv -> {
            TransaccionPago t = inv.getArgument(0);
            assertEquals("RECHAZADO", t.getEstadoPago());
            return t;
        });
        pagoService.procesar(dto);
        verify(pagoRepository).save(any(TransaccionPago.class));
    }
}
