package ca.gforcesoftware.recetteprojet.commands;

import ca.gforcesoftware.recetteprojet.domain.Difficulty;
import ca.gforcesoftware.recetteprojet.domain.Notes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-14
 */
@Setter
@Getter
@NoArgsConstructor
public class RecetteCommand {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String direction;
    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private Set<CategoryCommand> categories = new HashSet<>();
    private NotesCommand notes;
    private Byte[] image;


}
