package ca.gforcesoftware.recetteprojet.converters;

import ca.gforcesoftware.recetteprojet.commands.CategoryCommand;
import ca.gforcesoftware.recetteprojet.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-10-14
 */
public class CategoryToCategoryCommandTest {
    private final Long ID_VALUE = 1L;
    private final String DESCRIPTION_VALUE = "description";
    private CategoryToCategoryCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new CategoryToCategoryCommand();

    }

    @Test
    public void convert() {
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION_VALUE);
        CategoryCommand command = converter.convert(category);
        assertEquals(ID_VALUE, command.getId());
        assertEquals(DESCRIPTION_VALUE, command.getDescription());


    }
}