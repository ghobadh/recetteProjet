package ca.gforcesoftware.recetteprojet.converters;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


/**
 * @author gavinhashemi on 2024-10-14
 */
@Component
public class RecetteToRecetteCommand  implements Converter<Recette, RecetteCommand> {

    private final CategoryToCategoryCommand categoryToCategoryCommand;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final NotesToNotesCommand notesToNotesCommand;

    public RecetteToRecetteCommand(CategoryToCategoryCommand categoryToCategoryCommand,
                                   ca.gforcesoftware.recetteprojet.converters.IngredientToIngredientCommand ingredientToIngredientCommand,
                                   NotesToNotesCommand notesToNotesCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
    }


    @Synchronized
    @Nullable
    @Override
    public RecetteCommand convert(Recette source) {
        if (source == null) {
            return null;
        }
        RecetteCommand recetteCommand = new RecetteCommand();
        recetteCommand.setId(source.getId());
        recetteCommand.setDescription(source.getDescription());
        recetteCommand.setDifficulty(source.getDifficulty());
        recetteCommand.setServings(source.getServings());
        recetteCommand.setCookTime(source.getCookingTime());
        recetteCommand.setPrepTime(source.getPrepTime());
        recetteCommand.setDirection(source.getDirections());
        recetteCommand.setImage(source.getImage());
        recetteCommand.setNotes(notesToNotesCommand.convert(source.getNotes()));

        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().forEach( c -> recetteCommand.getCategories().add(categoryToCategoryCommand.convert(c)));
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients().forEach( i -> recetteCommand.getIngredients().add(ingredientToIngredientCommand.convert(i)));
        }

        return recetteCommand;
    }
}
