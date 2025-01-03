package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.converters.RecetteCommandToRecette;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.exceptions.NotFoundException;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
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

    @Mock
    private RecetteToRecetteCommand recetteToRecetteCommand;

    @Mock
    private RecetteCommandToRecette recetteCommandToRecette;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recetteService = new RecetteServiceImpl(recetteRepository, recetteCommandToRecette, recetteToRecetteCommand);
    }

    @Test(expected = NotFoundException.class)
    public void getRecetteByIdTestNotFoundException() throws Exception {
        Optional<Recette> recette = Optional.empty();
        when(recetteRepository.findById(1L)).thenReturn(recette);
        Recette recetteReturned = recetteService.findById(1L);

        // should be failed
    }

    @Test
    public void getRecetteById() {
        Recette recette = new Recette();
        recette.setId(1L);
        Optional<Recette> recetteOptional = Optional.of(recette);
        when(recetteRepository.findById(anyLong())).thenReturn(recetteOptional);
        Recette recetteReturn = recetteService.findById(1L);

        assertNotNull( recetteReturn);
    }

    @Test
    public void getRecettes() {
        Recette recette = new Recette();
        HashSet<Recette> recetteData  = new HashSet<>();
        recetteData.add(recette);

        when(recetteRepository.findAll()).thenReturn(recetteData);

        Set<Recette> recetteSet = recetteService.getRecettes();
        assertEquals(1, recetteSet.size());
        verify(recetteRepository, never()).findById(anyLong());
        verify(recetteRepository,times(1)).findAll();
    }

    @Test
    public void testDeleteById()  {
        Long idToDelete = 1L;
        recetteService.deleteById(idToDelete);

        //there is no any when clause, because delete is has void return

        verify(recetteRepository,times(1)).deleteById(anyLong());
    }
}