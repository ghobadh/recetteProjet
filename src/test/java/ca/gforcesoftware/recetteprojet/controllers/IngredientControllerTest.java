package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.services.IngredientService;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import ca.gforcesoftware.recetteprojet.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author gavinhashemi on 2024-10-15
 */
public class IngredientControllerTest {
    @Mock
    RecetteService recetteService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    IngredientController ingredientController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientController = new IngredientController(recetteService, ingredientService, unitOfMeasureService);
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

    @Test
    public void testShowIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findByRecetteIDAndIngredientId(anyLong(),anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recette/1/ingredient/2/show" ))
                .andExpect(status().isOk())
                .andExpect(view().name("/recette/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(2L);
        ingredientCommand.setRecetteId(3L);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recette/2/ingredient")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description", "some strings")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recette/3/ingredient/2/show"));
    }
}