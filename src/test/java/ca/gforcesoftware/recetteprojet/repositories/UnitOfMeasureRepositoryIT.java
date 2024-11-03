package ca.gforcesoftware.recetteprojet.repositories;

import ca.gforcesoftware.recetteprojet.bootstrap.RecetteBootStrap;
import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import ca.gforcesoftware.recetteprojet.repositories.reactive.CategoryReactiveRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.RecetteReactiveRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-10-13
 */
@RunWith(SpringRunner.class)
//I changed this annotation from @DataJpaTest to @DataMongoTest
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @Autowired
    private RecetteReactiveRepository recetteReactiveRepository;


    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll();
        recetteReactiveRepository.deleteAll();
        unitOfMeasureReactiveRepository.deleteAll();
        RecetteBootStrap rbs = new RecetteBootStrap(categoryReactiveRepository,
                recetteReactiveRepository,unitOfMeasureReactiveRepository);
        rbs.onApplicationEvent(null);

    }

    @Test
    public void findByUom() {
        Mono<UnitOfMeasure>  unitOfMeasure = unitOfMeasureReactiveRepository.findByUom("Teaspoon");
        assertEquals("Teaspoon", unitOfMeasure.block().getUom());
    }

    @Test
    public void findByUomOunce() {
        Mono<UnitOfMeasure> unitOfMeasure = unitOfMeasureReactiveRepository.findByUom("Ounce");
        assertEquals("Ounce", unitOfMeasure.block().getUom());
    }
}