package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import reactor.core.publisher.Mono;

/**
 * @author gavinhashemi on 2024-10-16
 */
public interface IngredientService {
    Recette getRecetteById(String id);

    Mono<IngredientCommand> findByRecetteIDAndIngredientId(String recetteId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command);

    Mono<Void> deleteById(String recetteId, String ingredientId);
}
