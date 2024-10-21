package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.commands.RecetteCommand;
import ca.gforcesoftware.recetteprojet.domain.Recette;
import ca.gforcesoftware.recetteprojet.exceptions.BadRequestException;
import ca.gforcesoftware.recetteprojet.exceptions.NotFoundException;
import ca.gforcesoftware.recetteprojet.services.RecetteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static java.lang.Long.parseLong;

/**
 * @author gavinhashemi on 2024-10-11
 *
 */
/*
 * @GetMapping and @PostMapping are wrapper of @RequestMapping, so we don't need to re-use @RequestMapping
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
    @GetMapping("/recette/{id}/show")
    public String showById(@PathVariable String id, Model model){
        log.debug("showById method called");
        try {
           Long longValue = parseLong(id);
            model.addAttribute("recette", recetteService.findById(longValue));

        } catch (NumberFormatException e) {
            throw new BadRequestException("The id '" + id + "' is not a number");
        }

        return "recette/show";
    }


    @GetMapping("recette/nouvelle")
    public String newRecette(Model model){
        log.debug("-----> Nouvelle Recette method called");
        model.addAttribute("recette", new RecetteCommand());
        return "recette/recetteform";
    }

    @GetMapping("recette/{id}/update")
    public String updateRecette(@PathVariable String id, Model model){
        log.debug("-----> Update Recette method called");
        model.addAttribute("recette", recetteService.findCommandById(parseLong(id)));
        return "recette/recetteform";
    }

    //@RequestMapping(name = "recette", method = RequestMethod.POST)// <-- Old method


    //for validation I changed
    //from
    //saveOuUpdateRecette( @ModelAttribute RecetteCommand command)
    //to
    //saveOuUpdateRecette(@Valid @ModelAttribute("recette") RecetteCommand command)
    @PostMapping
    @RequestMapping({"/recette/","recette"})
    public String saveOuUpdateRecette(@Valid @ModelAttribute("recette") RecetteCommand command){
        log.debug("-----> saveOuUpdateRecette method called");
        RecetteCommand recetteCommand = recetteService.saveRecetteCommand(command);

       // return "redirect:/recette/" + recetteCommand.getId() +"/show";
        return "redirect:/";
    }

    @GetMapping("recette/{id}/delete")
    public String deleteRecette(@PathVariable String id){
        log.debug("-----> delete Recette method called. Recette id: " + id);
        recetteService.deleteById(parseLong(id));
        return "redirect:/";
    }

    /*
    Although I put this @ExceptionHandler for handling the exception, I need to add @ResponseStatus also
    to orderly works. If I would not add @ResponseStatus in here then Test testRecetteException would return 200 instead
    of 404 because application was able to render a file and show it .
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound( Exception ex){
        log.error("-----> handleNotFound method called." + ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        /*
        now I can add  <p th:text="${exception.getMessage()}"></p>
        so I can pass id to the error message
         */
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }




}
