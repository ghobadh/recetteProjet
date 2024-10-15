package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.services.RecetteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static java.lang.Long.parseLong;

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
        log.info("recetteMake method called");
            model.addAttribute("recettes", recetteService.getRecette());

        return "recette";
    }

    /*
    Spring uses id by default and it can recognize {id} easily.
    I add @PathVariable to it found the recette based on the id which is clicked.
     */
    @RequestMapping("/recette/show/{id}")
    public String showById(@PathVariable String id, Model model){
        log.info("showById method called");
        model.addAttribute("recette", recetteService.findById(parseLong(id)));

        return "recette/show";
    }
}
