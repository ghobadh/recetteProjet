package ca.gforcesoftware.recetteprojet.domain;


import org.junit.Before;
import org.junit.Test;


import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author gavinhashemi on 2024-10-12
 */
/*
This part is totally wrong after Spring 2.6. Junit does not work with version 4 anymore.
It should be Junit from junit group id and make sure the test are not comping from
Jupiter otherwise they will not runnable
 */
public class CategoryTest {
    Category category;

    @Before
    public void setUp() {
        category = new Category();
    }

    @Test
    public void setId() {
        String id = "100";
        category.setId(id);
        assertEquals(id, category.getId());
    }

    @Test
    public void setDescription() {
        String description = "description";
        category.setDescription(description);
        assertEquals(description, category.getDescription());
    }

    @Test
    public void setRecettes() {
        Recette recette = new Recette();
        recette.setId("100");
        recette.setDescription("description");
        recette.setPrepTime(20);
        recette.setDifficulty(Difficulty.HARD);
        recette.setDirections("Directions");

        Set<Recette> recettes = new HashSet<>();
        recettes.add(recette);
        category.setRecettes(recettes);
        assertEquals(recettes, category.getRecettes());

    }
}