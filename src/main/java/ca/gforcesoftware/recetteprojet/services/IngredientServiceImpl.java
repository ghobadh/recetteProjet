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

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recette> recipeOptional = recetteRepository.findById(command.getRecetteId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecetteId());
            return new IngredientCommand();
        } else {
            Recette recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUnitOfMeasure(unitOfMeasureRepository
                        .findById(command.getUomCommand().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));
            }

            Recette savedRecipe = recetteRepository.save(recipe);

            Optional<Ingredient> optionalrec =savedRecipe
                    .getIngredients()
                    .stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            if (!optionalrec.isPresent()) {
                log.error("Could not find ingredient with id " + command.getId());
                return new IngredientCommand();
            } else {
                return ingredientToIngredientCommand.convert(optionalrec.get());
            }
        }

    }

}
