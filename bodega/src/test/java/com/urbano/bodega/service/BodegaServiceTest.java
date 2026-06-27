package com.urbano.bodega.service;

import com.urbano.bodega.dto.MovimientoDTO;
import com.urbano.bodega.model.MovimientoInventario;
import com.urbano.bodega.repository.MovimientoRepository;
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
@DisplayName("Tests unitarios - BodegaService")
class BodegaServiceTest {

    @Mock private MovimientoRepository movimientoRepository;
    @InjectMocks private BodegaService bodegaService;

    private MovimientoInventario movimiento;

    @BeforeEach
    void setUp() {
        movimiento = new MovimientoInventario();
        movimiento.setId(1L);
        movimiento.setSku("URB-001");
        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(100);
        movimiento.setMotivo("Restock inicial");
    }

    @Test
    @DisplayName("getAll - debería retornar todos los movimientos")
    void testGetAll() {
        when(movimientoRepository.findAll()).thenReturn(List.of(movimiento));
        List<MovimientoInventario> result = bodegaService.getAll();
        assertEquals(1, result.size());
        verify(movimientoRepository).findAll();
    }

    @Test
    @DisplayName("getBySku - debería retornar movimientos por SKU")
    void testGetBySku() {
        when(movimientoRepository.findBySku("URB-001")).thenReturn(List.of(movimiento));
        List<MovimientoInventario> result = bodegaService.getBySku("URB-001");
        assertFalse(result.isEmpty());
        assertEquals("ENTRADA", result.get(0).getTipo());
    }

    @Test
    @DisplayName("registrar - debería guardar movimiento y retornarlo")
    void testRegistrar() {
        MovimientoDTO dto = new MovimientoDTO("URB-001", "ENTRADA", 50, "Compra proveedor");
        when(movimientoRepository.save(any())).thenReturn(movimiento);
        MovimientoInventario result = bodegaService.registrar(dto);
        assertNotNull(result);
        verify(movimientoRepository).save(any(MovimientoInventario.class));
    }
}
