package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.RecetteCommandToRecette;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-11
 */
@Slf4j
@Service
public class RecetteServiceImpl implements RecetteService {
    private final RecetteRepository recetteRepository;
    private final RecetteCommandToRecette recetteCommandToRecette;
    private final RecetteToRecetteCommand recetteToRecetteCommand;

    public RecetteServiceImpl(RecetteRepository recetteRepository, RecetteCommandToRecette recetteCommandToRecette, RecetteToRecetteCommand recetteToRecetteCommand) {
        this.recetteRepository = recetteRepository;
        this.recetteCommandToRecette = recetteCommandToRecette;
        this.recetteToRecetteCommand = recetteToRecetteCommand;
    }

    @Override
    public Set<Recette> getRecette() {
        log.debug("getRecette is called in debug mode");
        Set<Recette> recettes = new HashSet<>();
        recetteRepository.findAll().forEach(recettes::add);
        return recettes;
    }

    @Override
    public Recette findById(Long id) {
        Optional<Recette> recette= recetteRepository.findById(id);
        if(!recette.isPresent()){
            throw new RuntimeException("Recette not found");
        }
        return recette.get();
    }

    @Override
    public RecetteCommand saveRecetteCommand(RecetteCommand recetteCommand) {
        Recette recette = recetteCommandToRecette.convert(recetteCommand);
        Recette savedRecette = recetteRepository.save(recette);
        log.info("saved recette to database");
        return recetteToRecetteCommand.convert(savedRecette);
    }
}
