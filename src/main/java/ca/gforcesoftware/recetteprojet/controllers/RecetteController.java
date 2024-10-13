package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.services.RecetteService;
import ca.gforcesoftware.recetteprojet.services.RecetteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gavinhashemi on 2024-10-11
 */
@Slf4j
@Controller
public class RecetteController {

    public final RecetteService recetteService;

    public RecetteController(RecetteService recetteService) {
        this.recetteService = recetteService;
    }

    @RequestMapping({"/recette","/recette.html"})
    public String recetteMake(Model model) {
        log.debug("recetteMake method called");
            model.addAttribute("recettes", recetteService.getRecette());

        return "recette";
    }
}