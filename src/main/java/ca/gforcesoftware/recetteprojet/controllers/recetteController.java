package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.services.RecetteServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gavinhashemi on 2024-10-11
 */
@Controller
public class recetteController {

    public final RecetteServiceImpl recetteService;

    public recetteController(RecetteServiceImpl recetteService) {
        this.recetteService = recetteService;
    }

    @RequestMapping({"/recette","/recette.html"})
    public String recetteMake(Model model) {
        model.addAttribute("recettes", recetteService.getRecette());

        return "recette";
    }
}
