package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.converters.RecetteCommandToRecette;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.exceptions.NotFoundException;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.reactive.RecetteReactiveRepository;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author gavinhashemi on 2024-10-13
 */
public class RecetteServiceImplTest {

    private RecetteServiceImpl recetteService;

    @Mock
    private RecetteReactiveRepository recetteReactiveRepository;

    @Mock
    private RecetteToRecetteCommand recetteToRecetteCommand;

    @Mock
    private RecetteCommandToRecette recetteCommandToRecette;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recetteService = new RecetteServiceImpl(recetteReactiveRepository, recetteCommandToRecette, recetteToRecetteCommand);
    }

    @Test(expected = NullPointerException.class)
    public void getRecetteByIdTestNotFoundException() throws Exception {
        Mono<Recette> recette = Mono.just(null);
        when(recetteReactiveRepository.findById("1")).thenReturn(recette);
        Recette recetteReturned = recetteService.findById("1");

        // should be failed
    }

    @Test
    public void getRecetteById() {
        Recette recette = new Recette();
        recette.setId("1");
        Mono<Recette> recetteOptional =Mono.just(recette);
        when(recetteReactiveRepository.findById(anyString())).thenReturn(recetteOptional);
        Recette recetteReturn = recetteService.findById("1");

        assertNotNull( recetteReturn);
    }

    @Test
    public void getRecettes() {
        Recette recette = new Recette();
        Flux<Recette> recetteData  = Flux.just(recette);
        //recetteData.add(recette);

        when(recetteReactiveRepository.findAll()).thenReturn(recetteData);

        Flux<Recette> recetteSet = recetteService.getRecettes();
        assertEquals(1, recetteSet.count().block());
        verify(recetteReactiveRepository, never()).findById(anyString());
        verify(recetteReactiveRepository,times(1)).findAll();
    }

    @Test
    public void testDeleteById()  {
        String idToDelete = "1";
        recetteService.deleteById(idToDelete);

        //there is no any when clause, because delete is has void return

        verify(recetteReactiveRepository,times(1)).deleteById(anyString());
    }
}