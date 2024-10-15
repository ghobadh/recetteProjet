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
public class RecetteCommandToRecette implements Converter<RecetteCommand, Recette> {

    private final CategoryCommandToCategory categoryCommandToCategory;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final NotesCommandToNotes notesCommandToNotes;

    public RecetteCommandToRecette(CategoryCommandToCategory categoryCommandToCategory,
                                   IngredientCommandToIngredient ingredientCommandToIngredient,
                                   NotesCommandToNotes notesCommandToNotes) {
        this.categoryCommandToCategory = categoryCommandToCategory;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.notesCommandToNotes = notesCommandToNotes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recette convert(RecetteCommand source) {
        if (source == null) {
            return null;
        }
        final Recette recette = new Recette();
        recette.setId(source.getId());
        recette.setDirections(source.getDirection());
        recette.setDifficulty(source.getDifficulty());
        recette.setDescription(source.getDescription());
        recette.setPrepTime(source.getPrepTime());
        recette.setUrl(source.getUrl());
        recette.setServings(source.getServings());
        recette.setPrepTime(source.getPrepTime());
        recette.setCookingTime(source.getCookTime());
        recette.setNotes(notesCommandToNotes.convert(source.getNotes()));

        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().forEach(category -> {
               recette.getCategories().add(categoryCommandToCategory.convert(category)) ;
            });
        }
        if(source.getIngredients() != null && source.getIngredients().size() > 0) {
            source.getIngredients().forEach(ingredient -> {
                recette.getIngredients().add(ingredientCommandToIngredient.convert(ingredient));
            });
        }

        return recette;
    }
}
