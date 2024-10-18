package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;

/**
 * @author gavinhashemi on 2024-10-16
 */
public interface IngredientService {
    Recette getRecetteById(Long id);

    IngredientCommand findByRecetteIDAndIngredientId(Long recetteId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteById(Long recetteId, Long ingredientId);
}
