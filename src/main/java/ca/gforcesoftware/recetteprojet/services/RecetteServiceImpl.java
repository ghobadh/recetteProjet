package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.RecetteCommandToRecette;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.exceptions.NotFoundException;
import ca.gforcesoftware.recetteprojet.repositories.reactive.RecetteReactiveRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author gavinhashemi on 2024-10-11
 */
@Slf4j
@Service
public class RecetteServiceImpl implements RecetteService {
    private final RecetteReactiveRepository recetteReactiveRepository;
    private final RecetteCommandToRecette recetteCommandToRecette;
    private final RecetteToRecetteCommand recetteToRecetteCommand;

    public RecetteServiceImpl(RecetteReactiveRepository recetteReactiveRepository,
                              RecetteCommandToRecette recetteCommandToRecette,
                              RecetteToRecetteCommand recetteToRecetteCommand) {
        this.recetteReactiveRepository = recetteReactiveRepository;
        this.recetteCommandToRecette = recetteCommandToRecette;
        this.recetteToRecetteCommand = recetteToRecetteCommand;
    }

    @Override
    public Flux<Recette> getRecettes() {
        log.debug("getRecette is called in debug mode");
        //Set<Recette> recettes = new HashSet<>();
        //recetteRepository.findAll().forEach(recettes::add);
       // return recettes;
        return  recetteReactiveRepository.findAll();
    }

    @Override
    public Recette findById(String id) {
        Mono<Recette> recette= recetteReactiveRepository.findById(id);
        recette.hasElement().subscribe(hasElement -> {
            if (hasElement) {
                log.info("recette found");
            } else {
                throw new NotFoundException("Recette id not found for given id: " + id.toString());
            }
        });
        /*if(!recette.isPresent()){
           // throw new RuntimeException("Recette not found");
            throw new NotFoundException("Recette id not found for given id: " + id.toString());
        } */
        return recette.block();
    }

    @Transactional
    @Override
    public RecetteCommand saveRecetteCommand(RecetteCommand recetteCommand) {
        Recette recette = recetteCommandToRecette.convert(recetteCommand);
        log.info("recette ID: " + recette.getId());
        Recette savedRecette = recetteReactiveRepository.save(recette).block();
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
        recetteReactiveRepository.deleteById(l);

    }
}
