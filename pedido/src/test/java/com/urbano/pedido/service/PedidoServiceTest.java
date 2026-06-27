package com.urbano.pedido.service;

import com.urbano.pedido.exception.PedidoNotFoundException;
import com.urbano.pedido.model.Pedido;
import com.urbano.pedido.repository.PedidoRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - PedidoService")
class PedidoServiceTest {

    @Mock private PedidoRepository pedidoRepository;
    @Mock private WebClient webClientCliente;
    @InjectMocks private PedidoService pedidoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setClienteId(5L);
        pedido.setDireccionId(2L);
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(new BigDecimal("59500"));
    }

    @Test
    @DisplayName("getAll - debería retornar todos los pedidos")
    void testGetAll() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));
        List<Pedido> result = pedidoService.getAll();
        assertEquals(1, result.size());
        verify(pedidoRepository).findAll();
    }

    @Test
    @DisplayName("getById - debería retornar pedido existente")
    void testGetById_existe() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        Pedido result = pedidoService.getById(1L);
        assertEquals("PENDIENTE", result.getEstado());
    }

    @Test
    @DisplayName("getById - debería lanzar excepción cuando no existe")
    void testGetById_noExiste() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(PedidoNotFoundException.class, () -> pedidoService.getById(99L));
    }

    @Test
    @DisplayName("getByCliente - debería retornar pedidos del cliente")
    void testGetByCliente() {
        when(pedidoRepository.findByClienteId(5L)).thenReturn(List.of(pedido));
        List<Pedido> result = pedidoService.getByCliente(5L);
        assertEquals(1, result.size());
        assertEquals(5L, result.get(0).getClienteId());
    }

    @Test
    @DisplayName("cambiarEstado - debería actualizar el estado del pedido")
    void testCambiarEstado() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        when(pedidoRepository.save(any())).thenReturn(pedido);
        Pedido result = pedidoService.cambiarEstado(1L, "PAGADO");
        assertEquals("PAGADO", pedido.getEstado());
        verify(pedidoRepository).save(pedido);
    }
}
