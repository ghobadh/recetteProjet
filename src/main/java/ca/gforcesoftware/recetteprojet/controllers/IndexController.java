package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.services.RecetteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author gavinhashemi on 2024-10-09
 */
@Controller
public class IndexController {
   /* private CategoryRepository CategoryRepository;
    private UnitOfMeasureRepository UnitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        CategoryRepository = categoryRepository;
        UnitOfMeasureRepository = unitOfMeasureRepository;
    }*/

    public IndexController(RecetteService recetteService) {
        this.recetteService = recetteService;
    }

    RecetteService recetteService;

    @RequestMapping({"","/","/index"})
    public String getIndexPage(Model model){
        System.out.println("Gargamel say something...");
       /* Optional<Category> categoryOptional = CategoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = UnitOfMeasureRepository.findByUom("Ounce");
        if (categoryOptional.isPresent()) {
            System.out.println(categoryOptional.get().getDescription() + " has id " + categoryOptional.get().getId());
        }
        if (unitOfMeasureOptional.isPresent()) {
            System.out.println(unitOfMeasureOptional.get().getUom() + " has id " + unitOfMeasureOptional.get().getId());
        } */
        model.addAttribute("recettes", recetteService.getRecettes().collectList().block());
        return "index";
    }
}
