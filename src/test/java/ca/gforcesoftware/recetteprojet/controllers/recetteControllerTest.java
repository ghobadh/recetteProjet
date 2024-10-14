package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @author gavinhashemi on 2024-10-13
 */
public class recetteControllerTest {

    @Mock
    RecetteService recetteService;

    @Mock
    Model model;

    RecetteController recetteController;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recetteController = new RecetteController(recetteService);
    }

    @Test
    public void recetteMake() {
        String viewName = recetteController.recetteMake(model);
        assertNotNull(viewName);
        assertEquals("recette", viewName);
        verify(recetteService, times(1)).getRecette();
        verify(model, times(1)).addAttribute(eq("recettes"), anySet());
    }

    @Test
    public void recetteMake_2(){
        Set<Recette> recetteSet = new HashSet<>();

        Recette recette = new Recette();
        recette.setId(5L);
        recetteSet.add(recette);
        Recette recette2 = new Recette();
        recette2.setId(2L);
        recetteSet.add(recette2);
        Recette recette3 = new Recette();
        recette3.setId(3L);
        recetteSet.add(recette3);

        when(recetteService.getRecette()).thenReturn(recetteSet);
        String viewName = recetteController.recetteMake(model);
        ArgumentCaptor<Set<Recette>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        verify(recetteService,times(1)).getRecette();
        verify(model, times(1)).addAttribute(eq("recettes"), argumentCaptor.capture());
        Set<Recette> setInController = argumentCaptor.getValue();
        assertEquals(3, setInController.size());

    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recetteController).build();
        mockMvc.perform(get("/recette.html"))
                .andExpect(status().isOk())
                .andExpect(view().name("recette"));

    }

    @Test
    public void testGetRecette() throws Exception {
        Recette recette = new Recette();
        recette.setId(1L);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recetteController).build();
        when(recetteService.findById(anyLong())).thenReturn(recette);

        mockMvc.perform(get("/recette/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recette/show"));
    }
}