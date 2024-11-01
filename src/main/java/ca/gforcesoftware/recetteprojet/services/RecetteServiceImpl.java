package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.RecetteCommandToRecette;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Set<Recette> getRecettes() {
        log.debug("getRecette is called in debug mode");
        Set<Recette> recettes = new HashSet<>();
        recetteRepository.findAll().forEach(recettes::add);
        return recettes;
    }

    @Override
    public Recette findById(String id) {
        Optional<Recette> recette= recetteRepository.findById(id);
        if(!recette.isPresent()){
           // throw new RuntimeException("Recette not found");
            throw new NotFoundException("Recette id not found for given id: " + id.toString());
        }
        return recette.get();
    }

    @Transactional
    @Override
    public RecetteCommand saveRecetteCommand(RecetteCommand recetteCommand) {
        Recette recette = recetteCommandToRecette.convert(recetteCommand);
        log.info("recette ID: " + recette.getId());
        Recette savedRecette = recetteRepository.save(recette);
        log.info(" --- saved recette to database ----");
        return recetteToRecetteCommand.convert(savedRecette);
    }

    @Override
    @Transactional
    public RecetteCommand findCommandById(String id) {
        RecetteCommand recetteCommand = recetteToRecetteCommand.convert(findById(id));
        return recetteCommand;
    }

    @Override
    @Transactional
    public void deleteById(String l) {
        recetteRepository.deleteById(l);

    }
}
