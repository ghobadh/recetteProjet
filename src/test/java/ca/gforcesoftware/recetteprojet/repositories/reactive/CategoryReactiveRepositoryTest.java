package ca.gforcesoftware.recetteprojet.repositories.reactive;

import ca.gforcesoftware.recetteprojet.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-11-01
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {
    @Autowired
    private CategoryReactiveRepository repo;

    @Before
    public void setUp() {
        repo.deleteAll().block();
    }

    @Test
    public void findByDescription() {
        Category c1 = new Category();
        c1.setDescription("cat");
        repo.save(c1).block();
        Category c2 = new Category();
        c2.setDescription("dog");
        repo.save(c2).block();
        Mono<Category> categories = repo.findByDescription("cat");
        assertEquals(c1.getDescription(), categories.block().getDescription());
    }

    @Test
    public void find(){
        Category c1 = new Category();
        c1.setDescription("cat");
        repo.save(c1).block();
        Category c2 = new Category();
        c2.setDescription("dog");
        repo.save(c2).block();

        Category fc = repo.findById(c1.getId()).block();
        assertEquals(c1.getDescription(), fc.getDescription());
    }

}