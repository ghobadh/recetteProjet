package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.IngredientCommandToIngredient;
import ca.gforcesoftware.recetteprojet.converters.IngredientToIngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.UnitOfMeasureCommandToUnitOfMeasure;
import ca.gforcesoftware.recetteprojet.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.domain.Ingredient;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * @author gavinhashemi on 2024-10-16
 */
public class IngredientServiceImplTest {


    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private  final IngredientCommandToIngredient ingredientCommandToIngredient;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    RecetteRepository recetteRepository;

    IngredientService ingredientService;


    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand,
                ingredientCommandToIngredient, recetteRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecetteIDAndIngredientId() {
        Recette recette = new Recette();
        recette.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recette.addIngredient(ingredient1);
        recette.addIngredient(ingredient2);
        recette.addIngredient(ingredient3);

        Optional<Recette> recetteOptional = Optional.of(recette);

        when(recetteRepository.findById(anyLong())).thenReturn(recetteOptional);

        IngredientCommand ingredientCommand = ingredientService.findByRecetteIDAndIngredientId(1L, 3L);

        assertEquals(Long.valueOf(1L), ingredientCommand.getRecetteId());
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        verify(recetteRepository,times(1)).findById(anyLong());
    }

    @Test
    public void testSaveRecetteCommand() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(3L);
        ingredientCommand.setRecetteId(2L);

        Optional<Recette> recetteOptional = Optional.of(new Recette());
        Recette recette = new Recette();
        recette.addIngredient( new Ingredient());
        recette.getIngredients().iterator().next().setId(3L);

        when(recetteRepository.findById(anyLong())).thenReturn(recetteOptional);
        when(recetteRepository.save(any())).thenReturn(recette);

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recetteRepository,times(1)).findById(anyLong());
        verify(recetteRepository,times(1)).save(any());

    }

    @Test
    public void testDeleteIngredient() {
        // Since delete ingredient does return void , I try to check wethere the recette save is working fine or not.

        Recette recette = new Recette();
        recette.setId(9L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        recette.addIngredient(ingredient1);
        ingredient1.setRecette(recette);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        recette.addIngredient(ingredient2);
        ingredient2.setRecette(recette);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        recette.addIngredient(ingredient3);
        ingredient3.setRecette(recette);


        Optional<Recette> recetteOptional = Optional.of(recette);
        when(recetteRepository.findById(anyLong())).thenReturn(recetteOptional);

        ingredientService.deleteById(9L,1L);

        verify(recetteRepository,times(1)).findById(anyLong());
        //Note this one , I want to select any() as recette object
        verify(recetteRepository,times(1)).save(any(Recette.class));
        //The line below does not work and it raise a mockito misusing exception!!
        //verify(ingredientService,times(1)).deleteById(anyLong(),anyLong());



    }
}