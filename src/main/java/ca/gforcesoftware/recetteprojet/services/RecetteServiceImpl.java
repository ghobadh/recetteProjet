package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-11
 */
@Slf4j
@Service
public class RecetteServiceImpl implements RecetteService {
    private final RecetteRepository recetteRepository;

    public RecetteServiceImpl(RecetteRepository recetteRepository) {
        this.recetteRepository = recetteRepository;
    }

    @Override
    public Set<Recette> getRecette() {
        log.debug("getRecette is called in debug mode");
        Set<Recette> recettes = new HashSet<>();
        recetteRepository.findAll().forEach(recettes::add);
        return recettes;
    }
}
