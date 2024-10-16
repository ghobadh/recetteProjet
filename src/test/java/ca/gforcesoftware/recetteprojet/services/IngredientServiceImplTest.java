package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.IngredientToIngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.domain.Ingredient;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
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

    @Mock
    RecetteRepository recetteRepository;

    IngredientService ingredientService;

    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, recetteRepository);
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
}