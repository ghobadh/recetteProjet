package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author gavinhashemi on 2024-10-15
 */
public class IngredientControllerTest {
    @Mock
    RecetteService recetteService;

    IngredientController ingredientController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recetteService);
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

    }

    @Test
    public void testListIngredient() throws Exception {
        RecetteCommand recetteCommand = new RecetteCommand();
        when(recetteService.findCommandById(anyLong())).thenReturn(recetteCommand);

        mockMvc.perform(get("/recette/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recette/ingredient/list"))
                .andExpect(model().attributeExists("recette"));

    }
}