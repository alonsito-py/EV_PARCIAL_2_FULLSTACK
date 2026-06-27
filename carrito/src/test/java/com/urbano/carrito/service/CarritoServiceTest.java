package com.urbano.carrito.service;

import com.urbano.carrito.exception.CarritoNotFoundException;
import com.urbano.carrito.model.Carrito;
import com.urbano.carrito.repository.CarritoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - CarritoService")
class CarritoServiceTest {

    @Mock private CarritoRepository carritoRepository;
    @Mock private WebClient webClientInventario;
    @InjectMocks private CarritoService carritoService;

    private Carrito carrito;

    @BeforeEach
    void setUp() {
        carrito = new Carrito();
        carrito.setId(1L);
        carrito.setClienteId(5L);
        carrito.setEstado("ACTIVO");
    }

    @Test
    @DisplayName("getOrCreate - debería retornar carrito activo existente")
    void testGetOrCreate_carritoExistente() {
        when(carritoRepository.findByClienteIdAndEstado(5L, "ACTIVO")).thenReturn(Optional.of(carrito));
        Carrito result = carritoService.getOrCreate(5L);
        assertEquals("ACTIVO", result.getEstado());
        verify(carritoRepository, never()).save(any());
    }

    @Test
    @DisplayName("getOrCreate - debería crear carrito cuando no existe")
    void testGetOrCreate_nuevoCarrito() {
        when(carritoRepository.findByClienteIdAndEstado(5L, "ACTIVO")).thenReturn(Optional.empty());
        when(carritoRepository.save(any())).thenReturn(carrito);
        Carrito result = carritoService.getOrCreate(5L);
        assertNotNull(result);
        verify(carritoRepository).save(any(Carrito.class));
    }

    @Test
    @DisplayName("vaciar - debería limpiar items del carrito")
    void testVaciar() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any())).thenReturn(carrito);
        carritoService.vaciar(1L);
        assertTrue(carrito.getItems().isEmpty());
        verify(carritoRepository).save(carrito);
    }

    @Test
    @DisplayName("vaciar - debería lanzar excepción cuando carrito no existe")
    void testVaciar_noExiste() {
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CarritoNotFoundException.class, () -> carritoService.vaciar(99L));
    }

    @Test
    @DisplayName("procesar - debería cambiar estado a PROCESADO")
    void testProcesar() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(any())).thenReturn(carrito);
        carritoService.procesar(1L);
        assertEquals("PROCESADO", carrito.getEstado());
        verify(carritoRepository).save(carrito);
    }
}
