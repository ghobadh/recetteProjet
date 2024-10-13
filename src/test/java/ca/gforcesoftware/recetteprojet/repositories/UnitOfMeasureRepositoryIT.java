package ca.gforcesoftware.recetteprojet.repositories;

import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-10-13
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUom() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Teaspoon");
        assertTrue(unitOfMeasure.isPresent());
        assertEquals("Teaspoon", unitOfMeasure.get().getUom());
    }

    @Test
    public void findByUomOunce() {
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByUom("Ounce");
        assertTrue(unitOfMeasure.isPresent());
        assertEquals("Ounce", unitOfMeasure.get().getUom());
    }
}