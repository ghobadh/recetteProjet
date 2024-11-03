package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.commands.UnitOfMeasureCommand;
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
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;


import java.util.ArrayList;
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
    public void testNewIngredientForm() throws Exception  {
        RecetteCommand recetteCommand = new RecetteCommand();
        recetteCommand.setId("9");

        when(recetteService.findCommandById(anyString())).thenReturn(recetteCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new Flux<UnitOfMeasureCommand>() {
            @Override
            public void subscribe(CoreSubscriber<? super UnitOfMeasureCommand> actual) {

            }
        });

        mockMvc.perform(get("/recette/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recette/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recetteService,times(1)).findCommandById(anyString());
    }

    @Test
    public void testListIngredient() throws Exception {
        RecetteCommand recetteCommand = new RecetteCommand();
        when(recetteService.findCommandById(anyString())).thenReturn(recetteCommand);

        mockMvc.perform(get("/recette/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("/recette/ingredient/list"))
                .andExpect(model().attributeExists("recette"));

    }

    @Test
    public void testShowIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.findByRecetteIDAndIngredientId(anyString(),anyString())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recette/1/ingredient/2/show" ))
                .andExpect(status().isOk())
                .andExpect(view().name("/recette/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId("2");
        ingredientCommand.setRecetteId("3");

        when(ingredientService.saveIngredientCommand(any())).thenReturn(ingredientCommand);

        mockMvc.perform(post("/recette/2/ingredient")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id","")
                        .param("description", "some strings")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recette/3/ingredient/2/show"));
    }

    @Test
    public void testDeleteIngredient() throws Exception {
        mockMvc.perform(get("/recette/1/ingredient/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recette/1/ingredients"));

        verify(ingredientService, times(1)).deleteById(anyString(),anyString());
    }
}