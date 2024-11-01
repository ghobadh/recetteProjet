package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;

/**
 * @author gavinhashemi on 2024-10-16
 */
public interface IngredientService {
    Recette getRecetteById(String id);

    IngredientCommand findByRecetteIDAndIngredientId(String recetteId, String ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(String recetteId, String ingredientId);
}
