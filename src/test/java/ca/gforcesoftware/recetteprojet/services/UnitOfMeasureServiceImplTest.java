package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.UnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import ca.gforcesoftware.recetteprojet.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * @author gavinhashemi on 2024-10-16
 */
public class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand); ;

    }

    @Test
    public void listAllUoms() {
        List<UnitOfMeasure> setUnitOfMeasures = new ArrayList<>();
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId("1");
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId("2");
        setUnitOfMeasures.add(unitOfMeasure1);
        setUnitOfMeasures.add(unitOfMeasure2);

        when(unitOfMeasureRepository.findAll()).thenReturn(setUnitOfMeasures);

        List<UnitOfMeasureCommand> suom = unitOfMeasureService.listAllUoms();
        verify(unitOfMeasureRepository,times(1)).findAll();

    }
}