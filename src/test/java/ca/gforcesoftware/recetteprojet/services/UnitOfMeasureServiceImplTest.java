package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.UnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import ca.gforcesoftware.recetteprojet.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author gavinhashemi on 2024-10-16
 */
public class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;


    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    private UnitOfMeasureService unitOfMeasureService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository, unitOfMeasureToUnitOfMeasureCommand); ;

    }

    @Test
    public void listAllUoms() {
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId("1");
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId("2");

        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(unitOfMeasure1,unitOfMeasure2));

        List<UnitOfMeasureCommand> suom = unitOfMeasureService.listAllUoms().collectList().block();
        verify(unitOfMeasureReactiveRepository,times(1)).findAll();
        assertEquals(2,suom.size());

    }
}