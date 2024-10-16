package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

   /* @RequestMapping({"recette.html", "recette"})
    public String recetteMake(Model model) {
        log.info("----> recetteMake method called");
            model.addAttribute("recettes", recetteService.getRecette());

        return "recette";
    } */

    /*
    Spring uses id by default and it can recognize {id} easily.
    I add @PathVariable to it found the recette based on the id which is clicked.
     */
    @GetMapping
    @RequestMapping("/recette/{id}/show")
    public String showById(@PathVariable String id, Model model){
        log.info("showById method called");
        model.addAttribute("recette", recetteService.findById(parseLong(id)));

        return "recette/show";
    }

    @GetMapping
    @RequestMapping("recette/nouvelle")
    public String newRecette(Model model){
        log.info("-----> Nouvelle Recette method called");
        model.addAttribute("recette", new RecetteCommand());
        return "recette/recetteform";
    }

    @GetMapping
    @RequestMapping("recette/{id}/update")
    public String updateRecette(@PathVariable String id, Model model){
        log.debug("-----> Update Recette method called");
        model.addAttribute("recette", recetteService.findCommandById(parseLong(id)));
        return "recette/recetteform";
    }

    //@RequestMapping(name = "recette", method = RequestMethod.POST)// <-- Old method
    @PostMapping
    @RequestMapping({"/recette/","recette"})
    public String saveOuUpdateRecette(@ModelAttribute RecetteCommand command){
        log.debug("-----> saveOuUpdateRecette method called");
        RecetteCommand recetteCommand = recetteService.saveRecetteCommand(command);

       // return "redirect:/recette/" + recetteCommand.getId() +"/show";
        return "redirect:/";
    }

    @RequestMapping("recette/{id}/delete")
    public String deleteRecette(@PathVariable String id){
        log.debug("-----> delete Recette method called. Recette id: " + id);
        recetteService.deleteById(parseLong(id));
        return "redirect:/";
    }

}
