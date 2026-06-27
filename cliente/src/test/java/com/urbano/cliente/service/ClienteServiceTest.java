package com.urbano.cliente.service;

import com.urbano.cliente.dto.ClienteDTO;
import com.urbano.cliente.exception.ClienteNotFoundException;
import com.urbano.cliente.model.Cliente;
import com.urbano.cliente.repository.ClienteRepository;
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
@DisplayName("Tests unitarios - ClienteService")
class ClienteServiceTest {

    @Mock private ClienteRepository clienteRepository;
    @InjectMocks private ClienteService clienteService;

    private Cliente cliente;
    private ClienteDTO clienteDTO;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Juan Pérez", "juan@urbano.cl", "+56912345678", false, true);
        clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Juan Pérez");
        clienteDTO.setCorreo("juan@urbano.cl");
        clienteDTO.setTelefono("+56912345678");
    }

    @Test
    @DisplayName("getAll - debería retornar todos los clientes")
    void testGetAll() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        List<Cliente> result = clienteService.getAll();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(clienteRepository).findAll();
    }

    @Test
    @DisplayName("getById - debería retornar cliente existente")
    void testGetById_existe() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        Cliente result = clienteService.getById(1L);
        assertEquals("Juan Pérez", result.getNombre());
    }

    @Test
    @DisplayName("getById - debería lanzar excepción cuando no existe")
    void testGetById_noExiste() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ClienteNotFoundException.class, () -> clienteService.getById(99L));
    }

    @Test
    @DisplayName("create - debería crear cliente con correo único")
    void testCreate_exitoso() {
        when(clienteRepository.existsByCorreo("juan@urbano.cl")).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        Cliente result = clienteService.create(clienteDTO);
        assertNotNull(result);
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    @DisplayName("create - debería lanzar excepción con correo duplicado")
    void testCreate_correoDuplicado() {
        when(clienteRepository.existsByCorreo("juan@urbano.cl")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> clienteService.create(clienteDTO));
        verify(clienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("verificar - debería cambiar estado verificado a true")
    void testVerificar() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        clienteService.verificar(1L);
        assertTrue(cliente.isVerificado());
        verify(clienteRepository).save(cliente);
    }

    @Test
    @DisplayName("delete - debería desactivar cliente (soft delete)")
    void testDelete() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);
        clienteService.delete(1L);
        assertFalse(cliente.isActivo());
        verify(clienteRepository).save(cliente);
    }
}
