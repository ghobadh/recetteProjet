package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.NotesCommand;
import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.*;
import ca.gforcesoftware.recetteprojet.domain.Category;
import ca.gforcesoftware.recetteprojet.domain.Notes;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.exceptions.BadRequestException;
import ca.gforcesoftware.recetteprojet.exceptions.NotFoundException;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author gavinhashemi on 2024-10-13
 */
public class RecetteControllerTest {
    @Mock
    RecetteService recipeService;

    RecetteController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        controller = new RecetteController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionHandlerController())
                .build();
    }

    @Test
    public void testGetRecette() throws Exception {

        Recette recette = new Recette();
        recette.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recette);

        mockMvc.perform(get("/recette/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recette/show"))
                .andExpect(model().attributeExists("recette"));
    }

    @Test
    public void testGetNewRecetteForm() throws Exception {
        RecetteCommand command = new RecetteCommand();

        mockMvc.perform(get("/recette/nouvelle"))
                .andExpect(status().isOk())
                .andExpect(view().name("recette/recetteform"))
                .andExpect(model().attributeExists("recette"));
    }

    @Test
    public void testPostNewRecetteForm() throws Exception {
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(1L);

        RecetteCommand command = new RecetteCommand();
        command.setId(2L);
        command.setNotes(notesCommand);


        when(recipeService.saveRecetteCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recette")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "some string")
                        .param("direction" , "some data")

                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }



    @Test
    public void testGetUpdateView() throws Exception {
/*        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(1L);

        RecetteCommand command = new RecetteCommand();
        command.setId(2L);
        command.setNotes(notesCommand);*/
        CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();
        UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
        IngredientToIngredientCommand ingredientToIngredientCommand = new IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommand);
        NotesToNotesCommand notesToNotesCommand = new NotesToNotesCommand();


        CategoryCommandToCategory categoryCommandToCategory = new CategoryCommandToCategory();
        UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
        IngredientCommandToIngredient ingredientCommandToIngredient = new IngredientCommandToIngredient(unitOfMeasureCommandToUnitOfMeasure);
        NotesCommandToNotes notesCommandToNotes = new NotesCommandToNotes();

        RecetteService recetteService = mock(RecetteService.class);

        Notes notes = new Notes();
        notes.setId(1L);
        Recette recette = new Recette();
        recette.setId(2L);
        recette.setNotes(notes);
        notes.setRecette(recette);

        RecetteCommand command = new RecetteToRecetteCommand(categoryToCategoryCommand,ingredientToIngredientCommand, notesToNotesCommand).convert(recette);
        RecetteRepository repository = mock(RecetteRepository.class);
        Set<Recette> recettes = new HashSet<>();
        recettes.add(recette);
        repository.saveAll(recettes);
        RecetteCommandToRecette recetteCommandToRecette = new RecetteCommandToRecette(categoryCommandToCategory,ingredientCommandToIngredient,notesCommandToNotes);
        recetteService.saveRecetteCommand(command);
        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recette/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recette/recetteform"))
                .andExpect(model().attributeExists("recette"));
    }

    @Test
    public void testDeleteAction() throws Exception {
        mockMvc.perform(get("/recette/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(recipeService, times(1)).deleteById(anyLong());
    }

    @Test
    public void testRecetteBadRequest() throws Exception {

        mockMvc.perform(get("/recette/1RR/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }


    /*
    Because I added @ResponseStatus in the NotFoundException class file,
    It is trigger in here when explicitly I throw an exception
     */
    @Test
    public void testRecetteNotFound() throws Exception {
        Recette recette = new Recette();
        recette.setId(1L);

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recette/1/show"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRecetteException() throws Exception {
        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recette/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));


    }
}