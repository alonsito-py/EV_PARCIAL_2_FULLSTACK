package com.urbano.marketing.service;

import com.urbano.marketing.dto.CampaniaDTO;
import com.urbano.marketing.exception.MarketingNotFoundException;
import com.urbano.marketing.model.CampaniaSocial;
import com.urbano.marketing.repository.MarketingRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - MarketingService")
class MarketingServiceTest {

    @Mock private MarketingRepository marketingRepository;
    @InjectMocks private MarketingService marketingService;

    private CampaniaSocial campania;

    @BeforeEach
    void setUp() {
        campania = new CampaniaSocial(1L, "Verano Urbano", "Instagram", "¡Descuentos de verano!", "BORRADOR", LocalDate.now().plusDays(7));
    }

    @Test
    @DisplayName("getAll - debería retornar todas las campañas")
    void testGetAll() {
        when(marketingRepository.findAll()).thenReturn(List.of(campania));
        List<CampaniaSocial> result = marketingService.getAll();
        assertEquals(1, result.size());
        verify(marketingRepository).findAll();
    }

    @Test
    @DisplayName("getById - debería retornar campaña existente")
    void testGetById_existe() {
        when(marketingRepository.findById(1L)).thenReturn(Optional.of(campania));
        CampaniaSocial result = marketingService.getById(1L);
        assertEquals("Verano Urbano", result.getNombre());
    }

    @Test
    @DisplayName("getById - debería lanzar excepción cuando no existe")
    void testGetById_noExiste() {
        when(marketingRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(MarketingNotFoundException.class, () -> marketingService.getById(99L));
    }

    @Test
    @DisplayName("publicar - debería cambiar estado a PUBLICADA")
    void testPublicar() {
        when(marketingRepository.findById(1L)).thenReturn(Optional.of(campania));
        when(marketingRepository.save(any())).thenReturn(campania);
        marketingService.publicar(1L);
        assertEquals("PUBLICADA", campania.getEstado());
        verify(marketingRepository).save(campania);
    }
}
