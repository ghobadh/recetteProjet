package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.domain.Ingredient;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gavinhashemi on 2024-10-15
 */
@Slf4j
@Controller
public class IngredientController {
    private RecetteService recetteService;

    public IngredientController(RecetteService recetteService1) {
        this.recetteService = recetteService1;
    }

    @GetMapping
    @RequestMapping("/recette/{recetteId}/ingredients")
    public String getIngredients(@PathVariable Long recetteId, Model model) {
        log.debug("Getting ingredients for " + recetteId);
        //I need to use findCommandById() rather than findById() to avoid lazy load error in Thymeleaf
        model.addAttribute("recette", recetteService.findCommandById(recetteId));
        return "recette/ingredient/list";
    }

}
