package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.UnitOfMeasureCommand;

import java.util.List;
import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-16
 */
public interface UnitOfMeasureService {
    List<UnitOfMeasureCommand> listAllUoms();
}
