package ca.gforcesoftware.recetteprojet.converters;

import ca.gforcesoftware.recetteprojet.commands.CategoryCommand;
import ca.gforcesoftware.recetteprojet.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-10-14
 */
public class CategoryCommandToCategoryTest {
    private final String ID_VALUE = "1";
    private final String DESCRIPTION_VALUE = "description";

    private CategoryCommandToCategory converter;

    @Before
    public void setUp() throws Exception {
        converter = new CategoryCommandToCategory();
    }

    @Test
    public void convert() throws Exception {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(ID_VALUE);
        categoryCommand.setDescription(DESCRIPTION_VALUE);

        Category category = converter.convert(categoryCommand);
        assertEquals(ID_VALUE, category.getId());
        assertEquals(DESCRIPTION_VALUE, category.getDescription());

    }
}