package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.IngredientCommandToIngredient;
import ca.gforcesoftware.recetteprojet.converters.IngredientToIngredientCommand;
import ca.gforcesoftware.recetteprojet.domain.Ingredient;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.UnitOfMeasureRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.RecetteReactiveRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-16
 */
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecetteReactiveRepository recetteReactiveRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;


    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecetteReactiveRepository recetteReactiveRepository, UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recetteReactiveRepository = recetteReactiveRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }

    @Override
    public Recette getRecetteById(String id) {

        return recetteReactiveRepository.findById(id).block().orElse(null);
    }

    @Override
    public Mono<IngredientCommand> findByRecetteIDAndIngredientId(String recetteId, String ingredientId) {
        return recetteReactiveRepository
                .findById(recetteId)
                .flatMapIterable(Recette::getIngredients)
                .filter(x -> x.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ing -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ing);
                    command.setRecetteId(recetteId);
                    return command;
                });
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand command) {
        recetteReactiveRepository.findById(command.getRecetteId())
                .map(recette -> recette.getIngredients()
                        .stream()
                        .filter( ingredient -> ingredient.getId().equalsIgnoreCase(command.getId()))
                        .findFirst())
                .map( ing -> { if(ing.isPresent()) {
                    Ingredient ingredient = ing.get();
                    ingredient.setRecetteId(command.getRecetteId());
                    ingredient.setDescription(command.getDescription());
                    ingredient.setAmount(command.getAmount());
                    ingredient.setUnitOfMeasure(unitOfMeasureReactiveRepository.findById(command
                            .getUomCommand()
                            .getId()).block());

                    return ingredientToIngredientCommand.convert(ingredient);
                }else{

                }

                });






        Mono<Recette> recetteMono = recetteReactiveRepository.findById(command.getRecetteId());
        recetteMono.hasElement().subscribe(x -> {
            if(!x){
                log.error("Ne trouve pas cette recette avec id: {} ", command.getRecetteId());
               // return Mono.just(new IngredientCommand());
            }
        });

        if (!recetteMono.isPresent()) {

            //todo toss error if not found!
            log.error("Ne trouve pas cette recette avec id: {} ", command.getRecetteId());
            return Mono.just(new IngredientCommand());
        } else {
            Recette recipe = recetteMono.block();

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

            Recette savedRecipe = recetteReactiveRepository.save(recipe).block();

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
            return Mono.just(ingredientToIngredientCommand.convert(savedIngredientOptional.get()));
        }

    }

    @Override
    public Mono<Void> deleteById(String recetteId, String ingredientId) {
        Recette optionalRecette = recetteReactiveRepository.findById(recetteId).block();

        if (optionalRecette != null) {
            Recette recette = optionalRecette;
            Optional<Ingredient> optionalIngredient = optionalRecette
                    .getIngredients()
                    .stream()
                    .filter(i -> i.getId().equals(ingredientId))
                    .findFirst();
            if (optionalIngredient.isPresent()) {
                Ingredient ingredient = optionalIngredient.get();
               // ingredient.setRecette(null);
                recette.getIngredients().remove(optionalIngredient.get());
                recetteReactiveRepository.save(recette);
            }

        } else {
            log.error("Could not find recette with id {}", recetteId);
        }
        return Mono.empty();
    }


}
