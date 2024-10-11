package ca.gforcesoftware.recetteprojet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gavinhashemi on 2024-10-09
 */
@Controller
public class IndexController {
    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        System.out.println("Gargamel say something...");
        return "index";
    }
}
