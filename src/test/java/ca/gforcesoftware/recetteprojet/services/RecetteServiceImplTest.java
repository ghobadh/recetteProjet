package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author gavinhashemi on 2024-10-13
 */
public class RecetteServiceImplTest {

    private RecetteServiceImpl recetteService;

    @Mock
    private RecetteRepository recetteRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recetteService = new RecetteServiceImpl(recetteRepository);
    }

    @Test
    public void getRecette() {
        Recette recette = new Recette();
        HashSet<Recette> recetteData  = new HashSet<>();
        recetteData.add(recette);

        when(recetteRepository.findAll()).thenReturn(recetteData);

        Set<Recette> recetteSet = recetteService.getRecette();
        assertEquals(1, recetteSet.size());
        verify(recetteRepository,times(1)).findAll();
    }
}