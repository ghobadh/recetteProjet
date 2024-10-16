package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;

import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-11
 */
public interface RecetteService {
    Set<Recette> getRecettes();
    Recette findById(Long id);

    RecetteCommand saveRecetteCommand(RecetteCommand recetteCommand);

    RecetteCommand findCommandById(Long id);
}
