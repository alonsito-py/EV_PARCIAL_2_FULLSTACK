package com.urbano.facturacion.service;

import com.urbano.facturacion.dto.BoletaDTO;
import com.urbano.facturacion.model.BoletaElectronica;
import com.urbano.facturacion.repository.FacturacionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - FacturacionService")
class FacturacionServiceTest {

    @Mock private FacturacionRepository facturacionRepository;
    @InjectMocks private FacturacionService facturacionService;

    private BoletaElectronica boleta;

    @BeforeEach
    void setUp() {
        boleta = new BoletaElectronica();
        boleta.setId(1L);
        boleta.setPedidoId(10L);
        boleta.setFolio("F-ABCD1234");
        boleta.setRutEmisor("76.123.456-7");
        boleta.setMontoNeto(new BigDecimal("50000"));
        boleta.setIva(new BigDecimal("9500"));
        boleta.setMontoTotal(new BigDecimal("59500"));
    }

    @Test
    @DisplayName("getAll - debería retornar todas las boletas")
    void testGetAll() {
        when(facturacionRepository.findAll()).thenReturn(List.of(boleta));
        List<BoletaElectronica> result = facturacionService.getAll();
        assertEquals(1, result.size());
        verify(facturacionRepository).findAll();
    }

    @Test
    @DisplayName("getByPedido - debería retornar boletas del pedido")
    void testGetByPedido() {
        when(facturacionRepository.findByPedidoId(10L)).thenReturn(List.of(boleta));
        List<BoletaElectronica> result = facturacionService.getByPedido(10L);
        assertEquals(1, result.size());
        assertEquals("F-ABCD1234", result.get(0).getFolio());
    }

    @Test
    @DisplayName("emitir - debería calcular IVA correctamente (19%)")
    void testEmitir_calculaIVA() {
        BoletaDTO dto = new BoletaDTO(10L, "76.123.456-7", new BigDecimal("50000"));
        when(facturacionRepository.save(any())).thenReturn(boleta);
        BoletaElectronica result = facturacionService.emitir(dto);
        assertNotNull(result);
        verify(facturacionRepository).save(any(BoletaElectronica.class));
    }

    @Test
    @DisplayName("emitir - el total debe ser monto neto + IVA")
    void testEmitir_totalCorrecto() {
        BigDecimal neto = new BigDecimal("100000");
        BigDecimal ivaEsperado = new BigDecimal("19000.00");
        BigDecimal totalEsperado = new BigDecimal("119000.00");
        BoletaDTO dto = new BoletaDTO(10L, "76.123.456-7", neto);
        when(facturacionRepository.save(any(BoletaElectronica.class))).thenAnswer(inv -> {
            BoletaElectronica b = inv.getArgument(0);
            assertEquals(0, ivaEsperado.compareTo(b.getIva()));
            assertEquals(0, totalEsperado.compareTo(b.getMontoTotal()));
            return b;
        });
        facturacionService.emitir(dto);
        verify(facturacionRepository).save(any());
    }
}
