package tn.esprit.tnfoyer.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tnfoyer.entities.Foyer;
import tn.esprit.tnfoyer.repositories.FoyerRepository;
import tn.esprit.tnfoyer.services.implementation.FoyerService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoyerServiceTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerService foyerService;

    @Test
    void shouldReturnFoyerById() {
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");

        when(foyerRepository.findById(1L))
                .thenReturn(Optional.of(foyer));

        Object result = foyerService.getFoyer(1L);

        assertNotNull(result);
        assertEquals("Foyer A", foyer.getNomFoyer());
        verify(foyerRepository).findById(1L);
    }
}
