package ca.gforcesoftware.recetteprojet.commands;

import ca.gforcesoftware.recetteprojet.domain.Difficulty;
import ca.gforcesoftware.recetteprojet.domain.Notes;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-14
 */
@Setter
@Getter
@NoArgsConstructor
public class RecetteCommand {
        /* I removed the field and defined it as string because of Mongodb
        private Long id;*/
    private String id;

    @NotBlank
    @Size(min = 3 , max = 255)
    private String description;

    @Min(1)
    @Max(999)
    private Integer prepTime;

    @Min(1)
    @Max(999)
    private Integer cookTime;

    @Min(1)
    @Max(100)
    private Integer servings;
    private String source;

    @URL
    private String url;

    @NotBlank
    private String direction;

    private List<IngredientCommand> ingredients = new ArrayList<>();
    private Difficulty difficulty;
    private List<CategoryCommand> categories = new ArrayList<>();
    private NotesCommand notes;
    private Byte[] image;


}
