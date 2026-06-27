package com.urbano.inventario.service;

import com.urbano.inventario.dto.InventarioDTO;
import com.urbano.inventario.exception.InventarioNotFoundException;
import com.urbano.inventario.model.Inventario;
import com.urbano.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - InventarioService")
class InventarioServiceTest {

    @Mock private InventarioRepository inventarioRepository;
    @InjectMocks private InventarioService inventarioService;

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario(1L, "URB-001", 50, 10);
    }

    @Test
    @DisplayName("getAll - debería retornar todos los registros de inventario")
    void testGetAll() {
        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));
        List<Inventario> result = inventarioService.getAll();
        assertEquals(1, result.size());
        verify(inventarioRepository).findAll();
    }

    @Test
    @DisplayName("getBySku - debería retornar inventario por SKU existente")
    void testGetBySku_existe() {
        when(inventarioRepository.findBySku("URB-001")).thenReturn(Optional.of(inventario));
        Inventario result = inventarioService.getBySku("URB-001");
        assertEquals(50, result.getCantidadDisponible());
    }

    @Test
    @DisplayName("getBySku - debería lanzar excepción cuando SKU no existe")
    void testGetBySku_noExiste() {
        when(inventarioRepository.findBySku("NOPE")).thenReturn(Optional.empty());
        assertThrows(InventarioNotFoundException.class, () -> inventarioService.getBySku("NOPE"));
    }

    @Test
    @DisplayName("reducirStock - debería decrementar la cantidad disponible")
    void testReducirStock() {
        when(inventarioRepository.findBySku("URB-001")).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any())).thenReturn(inventario);
        Inventario result = inventarioService.reducirStock("URB-001", 10);
        assertEquals(40, inventario.getCantidadDisponible());
    }

    @Test
    @DisplayName("aumentarStock - debería incrementar la cantidad disponible")
    void testAumentarStock() {
        when(inventarioRepository.findBySku("URB-001")).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any())).thenReturn(inventario);
        inventarioService.aumentarStock("URB-001", 20);
        assertEquals(70, inventario.getCantidadDisponible());
    }
}
