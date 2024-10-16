package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.IngredientToIngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Ingredient;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-16
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final RecetteRepository recetteRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecetteRepository recetteRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recetteRepository = recetteRepository;
    }

    @Override
    public Recette getRecetteById(Long id) {

        return recetteRepository.findById(id).orElse(null);
    }

    @Override
    public IngredientCommand findByRecetteIDAndIngredientId(long recetteId, long ingredientId) {
        Optional<Recette> recette = recetteRepository.findById(recetteId);

        if (!recette.isPresent()) {
            log.error("Could not find recette with id {}", recetteId);
            return null;
        }
        Recette rec = recette.get();

        Optional<IngredientCommand> ingredientOptionalCommand = rec.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map (i ->  ingredientToIngredientCommand.convert(i))
                .findFirst();

        if (!ingredientOptionalCommand.isPresent()) {
            log.error("Could not find ingredient with id " + ingredientId);
            return null;
        }
        return ingredientOptionalCommand.get();

    }


}
