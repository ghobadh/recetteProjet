package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.UnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author gavinhashemi on 2024-10-16
 */
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll()
                .spliterator(),false)
                .map( i -> unitOfMeasureToUnitOfMeasureCommand.convert(i))
                .collect(Collectors.toSet());
    }
}
