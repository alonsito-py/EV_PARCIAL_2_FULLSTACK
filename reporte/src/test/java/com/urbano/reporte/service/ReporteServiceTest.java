package com.urbano.reporte.service;

import com.urbano.reporte.dto.CierreDTO;
import com.urbano.reporte.model.CierreVentaDiario;
import com.urbano.reporte.repository.ReporteRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitarios - ReporteService")
class ReporteServiceTest {

    @Mock private ReporteRepository reporteRepository;
    @InjectMocks private ReporteService reporteService;

    private CierreVentaDiario cierre;

    @BeforeEach
    void setUp() {
        cierre = new CierreVentaDiario(1L, LocalDate.now(), 25, new BigDecimal("450000"), "Día normal");
    }

    @Test
    @DisplayName("getAll - debería retornar todos los cierres de venta")
    void testGetAll() {
        when(reporteRepository.findAll()).thenReturn(List.of(cierre));
        List<CierreVentaDiario> result = reporteService.getAll();
        assertEquals(1, result.size());
        verify(reporteRepository).findAll();
    }

    @Test
    @DisplayName("getPorPeriodo - debería filtrar cierres por rango de fechas")
    void testGetPorPeriodo() {
        LocalDate inicio = LocalDate.now().minusDays(7);
        LocalDate fin = LocalDate.now();
        when(reporteRepository.findByFechaBetween(inicio, fin)).thenReturn(List.of(cierre));
        List<CierreVentaDiario> result = reporteService.getPorPeriodo(inicio, fin);
        assertEquals(1, result.size());
        assertEquals(25, result.get(0).getTotalPedidos());
    }

    @Test
    @DisplayName("registrar - debería guardar y retornar cierre de venta")
    void testRegistrar() {
        CierreDTO dto = new CierreDTO(LocalDate.now(), 30, new BigDecimal("600000"), "Día exitoso");
        when(reporteRepository.save(any())).thenReturn(cierre);
        CierreVentaDiario result = reporteService.registrar(dto);
        assertNotNull(result);
        verify(reporteRepository).save(any(CierreVentaDiario.class));
    }
}
