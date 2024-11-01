package ca.gforcesoftware.recetteprojet.repositories.reactive;

import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-11-01
 */
@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    private UnitOfMeasureReactiveRepository repo;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll().block();
    }

    @Test
    public void findByUom() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setUom("UOMeasure");
        repo.save(unitOfMeasure).block();
        assertNotNull(repo.findByUom("UOMeasure"));

        UnitOfMeasure uomMono = repo.findByUom("UOMeasure").block();

        assertEquals(unitOfMeasure.getUom(), uomMono.getUom());


    }
}