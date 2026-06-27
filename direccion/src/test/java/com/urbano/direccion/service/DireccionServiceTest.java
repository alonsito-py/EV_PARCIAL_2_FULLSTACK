package com.urbano.direccion.service;

import com.urbano.direccion.dto.DireccionDTO;
import com.urbano.direccion.exception.DireccionNotFoundException;
import com.urbano.direccion.model.Direccion;
import com.urbano.direccion.repository.DireccionRepository;
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
@DisplayName("Tests unitarios - DireccionService")
class DireccionServiceTest {

    @Mock private DireccionRepository direccionRepository;
    @InjectMocks private DireccionService direccionService;

    private Direccion direccion;
    private DireccionDTO direccionDTO;

    @BeforeEach
    void setUp() {
        direccion = new Direccion(1L, 5L, "Av. Providencia 1234", "Providencia", "Metropolitana", "Depto 301", true);
        direccionDTO = new DireccionDTO(5L, "Av. Providencia 1234", "Providencia", "Metropolitana", null, true);
    }

    @Test
    @DisplayName("getByCliente - debería retornar direcciones del cliente")
    void testGetByCliente() {
        when(direccionRepository.findByClienteId(5L)).thenReturn(List.of(direccion));
        List<Direccion> result = direccionService.getByCliente(5L);
        assertEquals(1, result.size());
        assertEquals("Providencia", result.get(0).getComuna());
    }

    @Test
    @DisplayName("getById - debería retornar dirección existente")
    void testGetById_existe() {
        when(direccionRepository.findById(1L)).thenReturn(Optional.of(direccion));
        Direccion result = direccionService.getById(1L);
        assertEquals("Av. Providencia 1234", result.getCalle());
    }

    @Test
    @DisplayName("getById - debería lanzar excepción cuando no existe")
    void testGetById_noExiste() {
        when(direccionRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(DireccionNotFoundException.class, () -> direccionService.getById(99L));
    }

    @Test
    @DisplayName("create - debería guardar dirección válida")
    void testCreate() {
        when(direccionRepository.save(any())).thenReturn(direccion);
        Direccion result = direccionService.create(direccionDTO);
        assertNotNull(result);
        verify(direccionRepository).save(any(Direccion.class));
    }

    @Test
    @DisplayName("delete - debería eliminar la dirección")
    void testDelete() {
        when(direccionRepository.findById(1L)).thenReturn(Optional.of(direccion));
        doNothing().when(direccionRepository).deleteById(1L);
        direccionService.delete(1L);
        verify(direccionRepository).deleteById(1L);
    }
}
