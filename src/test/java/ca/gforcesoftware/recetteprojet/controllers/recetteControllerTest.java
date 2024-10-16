package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;


import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

/**
 * @author gavinhashemi on 2024-10-13
 */
public class recetteControllerTest {

    @Mock
    RecetteService recetteService;

    @Mock
    Model model;

    MockMvc mockMvc;

    RecetteController recetteController;
    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recetteController = new RecetteController(recetteService);
        mockMvc = MockMvcBuilders.standaloneSetup(recetteController).build();
    }

    @Ignore
    public void recetteMake() {
      //  String viewName = recetteController.recetteMake(model);
     //   assertNotNull(viewName);
     //   assertEquals("recette", viewName);
        verify(recetteService, times(1)).getRecettes();
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

        when(recetteService.getRecettes()).thenReturn(recetteSet);
        ArgumentCaptor<Set<Recette>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
        verify(recetteService,times(1)).getRecettes();
        verify(model, times(1)).addAttribute(eq("recettes"), argumentCaptor.capture());
        Set<Recette> setInController = argumentCaptor.getValue();
        assertEquals(3, setInController.size());

    }

    @Test
    public void testMockMVC() throws Exception {

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

    @Test
    public void testGetNewRecetteForm() throws Exception {
        RecetteCommand receetteCommand = new RecetteCommand();
        receetteCommand.setId(2L);

        when(recetteService.saveRecetteCommand(any())).thenReturn(receetteCommand);

        mockMvc.perform(post("/recette")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id","")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recette/show/2"));

    }


    @Test
    public void testGetUpdateRecetteForm() throws Exception {
        RecetteCommand receetteCommand = new RecetteCommand();
        receetteCommand.setId(2L);

        when(recetteService.saveRecetteCommand(any())).thenReturn(receetteCommand);

        mockMvc.perform(get("/recette/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recette/recetteform"))
                .andExpect(model().attributeExists("recette"));


    }
}