package ca.gforcesoftware.recetteprojet.services;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.converters.RecetteCommandToRecette;
import ca.gforcesoftware.recetteprojet.converters.RecetteToRecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.RecetteReactiveRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;

import static org.junit.Assert.*;

/**
 * @author gavinhashemi on 2024-10-14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RecetteServiceIT {
    public static final String NEW_DESCRIPTION = "New description";

    @Autowired
    RecetteService recetteService;

    @Autowired
    RecetteReactiveRepository recetteRepository;

    @Autowired
    RecetteCommandToRecette recetteCommandToRecette;

    @Autowired
    RecetteToRecetteCommand recetteToRecetteCommand;

    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {
        Flux<Recette> recettes = recetteRepository.findAll();
        Recette testRecette = recettes.next().block();
        RecetteCommand recetteCommand = recetteToRecetteCommand.convert(testRecette);

        recetteCommand.setDescription(NEW_DESCRIPTION);
        RecetteCommand saveRecetteCommand = recetteService.saveRecetteCommand(recetteCommand);

        assertEquals(NEW_DESCRIPTION, saveRecetteCommand.getDescription());
        assertEquals(testRecette.getId(), saveRecetteCommand.getId());
        assertEquals(testRecette.getCategories().size(), saveRecetteCommand.getCategories().size());
        assertEquals(testRecette.getIngredients().size(), saveRecetteCommand.getIngredients().size());
    }

}