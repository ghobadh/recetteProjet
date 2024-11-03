package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-16
 */
public interface UnitOfMeasureService {
    Flux<UnitOfMeasureCommand> listAllUoms();
}
