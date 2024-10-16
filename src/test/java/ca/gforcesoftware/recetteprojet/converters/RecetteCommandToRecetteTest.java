package ca.gforcesoftware.recetteprojet.converters;

import ca.gforcesoftware.recetteprojet.commands.CategoryCommand;
import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.commands.NotesCommand;
import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-10-14
 */
public class RecetteCommandToRecetteTest {

    private final Long ID_VALUE = 1L;
    private final String DESCRIPTION = "description";
    private final String DIRECTION = "direction";
    private final String URL = "url";
    private final Long NOTES_ID = 2L;
    private final Long CATEGORY_ID = 3L;
    private final Long INGREDIENT_ID = 4L;
    private RecetteCommandToRecette converter;

    @Before
    public void setUp() throws Exception {
        converter = new RecetteCommandToRecette(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient( new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void convert() {

        RecetteCommand recetteCommand = new RecetteCommand();
        recetteCommand.setId(ID_VALUE);
        recetteCommand.setDescription(DESCRIPTION);
        recetteCommand.setDirection(DIRECTION);
        recetteCommand.setUrl(URL);

        CategoryCommand command = new CategoryCommand();
        command.setId(ID_VALUE);
        recetteCommand.getCategories().add(command);

        NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);
        recetteCommand.setNotes(notes);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(CATEGORY_ID);
        recetteCommand.getIngredients().add(ingredient);




        Recette recette = converter.convert(recetteCommand);

        assertNotNull(recette);
        assertEquals(ID_VALUE, recette.getId());
        assertEquals(DESCRIPTION, recette.getDescription());


    }
}