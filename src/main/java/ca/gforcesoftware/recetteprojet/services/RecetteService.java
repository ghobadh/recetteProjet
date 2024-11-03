package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import reactor.core.publisher.Flux;

import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-11
 */
public interface RecetteService {
    Flux<Recette> getRecettes();
    Recette findById(String id);

    RecetteCommand saveRecetteCommand(RecetteCommand recetteCommand);

    RecetteCommand findCommandById(String id);

    void deleteById(String l);
}
