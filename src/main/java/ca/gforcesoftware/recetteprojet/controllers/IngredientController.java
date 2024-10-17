package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.IngredientCommand;
import ca.gforcesoftware.recetteprojet.converters.IngredientToIngredientCommand;
import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import ca.gforcesoftware.recetteprojet.services.IngredientService;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import ca.gforcesoftware.recetteprojet.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author gavinhashemi on 2024-10-15
 */
@Slf4j
@Controller
public class IngredientController {
    private RecetteService recetteService;
    private IngredientService ingredientService;
    private UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecetteService recetteService, IngredientService ingredientService,
                                UnitOfMeasureService unitOfMeasureService) {
        this.recetteService = recetteService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping
    @RequestMapping("/recette/{recetteId}/ingredients")
    public String getIngredients(@PathVariable Long recetteId, Model model) {
        log.debug("Getting ingredients for " + recetteId);
        //I need to use findCommandById() rather than findById() to avoid lazy load error in Thymeleaf
        model.addAttribute("recette", recetteService.findCommandById(recetteId));
        return "/recette/ingredient/list";
    }

    @GetMapping
    @RequestMapping("recette/{recetteId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable Long recetteId, @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecetteIDAndIngredientId(recetteId, id));
        return "/recette/ingredient/show";
    }

    @GetMapping
    @RequestMapping("recette/{recetteId}/ingredient/{id}/update")
    public String updateIngredient(@PathVariable Long recetteId, @PathVariable Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecetteIDAndIngredientId(recetteId, id));
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());
        return "/recette/ingredient/ingredientform";
    }



    @PostMapping("recette/{recetteId}/ingredient")
    public String SaveOrUpdateIngredient(@ModelAttribute IngredientCommand command) {

        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);
        log.debug("Saving ingredient id {} " + savedCommand.getId());
        log.debug("Saving recette id {}" + savedCommand.getRecetteId());

        return "redirect:/recette/" + savedCommand.getRecetteId() + "/ingredient/" + savedCommand.getId() +"/show";
    }

}
