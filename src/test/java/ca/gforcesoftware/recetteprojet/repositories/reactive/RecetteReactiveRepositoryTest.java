package ca.gforcesoftware.recetteprojet.repositories.reactive;

import ca.gforcesoftware.recetteprojet.domain.Difficulty;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-11-01
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class RecetteReactiveRepositoryTest {
    @Autowired
    private RecetteReactiveRepository recetteReactiveRepository;

    @Before
    public void setUp() throws Exception {
        recetteReactiveRepository.deleteAll().block();
    }

    @Test
    public void TestRecetteReactiveRepository() {
        Recette recette = new Recette();
        recette.setDifficulty(Difficulty.HARD);

        recetteReactiveRepository.save(recette).block();
        assertEquals(recetteReactiveRepository.findById(recette.getId()).block().getDifficulty(), Difficulty.HARD);

        Long c = recetteReactiveRepository.count().block();
        assertTrue(c > 0);
        assertEquals(Long.valueOf(1), c);

    }
}