package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.IngredientCommandToIngredient;
import ca.gforcesoftware.recetteprojet.converters.IngredientToIngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.UnitOfMeasureCommandToUnitOfMeasure;
import ca.gforcesoftware.recetteprojet.domain.Ingredient;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-16
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecetteRepository recetteRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;


    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecetteRepository recetteRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recetteRepository = recetteRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public Recette getRecetteById(String id) {

        return recetteRepository.findById(id).orElse(null);
    }

    @Override
    public IngredientCommand findByRecetteIDAndIngredientId(String recetteId, String ingredientId) {
        Optional<Recette> recette = recetteRepository.findById(recetteId);

        if (!recette.isPresent()) {
            log.error("Could not find recette with id {}", recetteId);
            return null;
        }
        Recette rec = recette.get();

        Optional<IngredientCommand> ingredientOptionalCommand = rec.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(i -> ingredientToIngredientCommand.convert(i))
                .findFirst();

        if (!ingredientOptionalCommand.isPresent()) {
            log.error("Could not find ingredient with id " + ingredientId);
            return null;
        }
        return ingredientOptionalCommand.get();

    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recette> recipeOptional = recetteRepository.findById(command.getRecetteId());

        if (!recipeOptional.isPresent()) {

            //todo toss error if not found!
            log.error("Ne trouve pas cette recette avec id: {} ", command.getRecetteId());
            return new IngredientCommand();
        } else {
            Recette recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(command.getUomCommand().getId())
                        .orElseThrow(() -> new RuntimeException("NE TROUVE PAS UOM")));
            } else {
                //add new Ingredient
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
               // ingredient.setRecette(recipe);
                recipe.addIngredient(ingredient);

            }

            Recette savedRecipe = recetteRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe
                    .getIngredients()
                    .stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if (!savedIngredientOptional.isPresent()) {
                log.error("Could not find ingredient with id " + command.getId());
                savedIngredientOptional = savedRecipe.getIngredients()
                        .stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUnitOfMeasure().getId().equals(command.getUomCommand().getId()))
                        .findFirst();

            }
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }

    }

    @Override
    public void deleteById(String recetteId, String ingredientId) {
        Optional<Recette> optionalRecette = recetteRepository.findById(recetteId);

        if (optionalRecette.isPresent()) {
            Recette recette = optionalRecette.get();
            Optional<Ingredient> optionalIngredient = optionalRecette
                    .get()
                    .getIngredients()
                    .stream()
                    .filter(i -> i.getId().equals(ingredientId))
                    .findFirst();
            if (optionalIngredient.isPresent()) {
                Ingredient ingredient = optionalIngredient.get();
               // ingredient.setRecette(null);
                recette.getIngredients().remove(optionalIngredient.get());
                recetteRepository.save(recette);
            }

        } else {
            log.error("Could not find recette with id {}", recetteId);
        }
    }
}
