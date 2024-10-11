package ca.gforcesoftware.recetteprojet.controllers;

import ca.gforcesoftware.recetteprojet.domain.Category;
import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import ca.gforcesoftware.recetteprojet.repositories.CategoryRepository;
import ca.gforcesoftware.recetteprojet.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-09
 */
@Controller
public class IndexController {
    private CategoryRepository CategoryRepository;
    private UnitOfMeasureRepository UnitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository) {
        CategoryRepository = categoryRepository;
        UnitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","/index"})
    public String getIndexPage(){
        System.out.println("Gargamel say something...");
        Optional<Category> categoryOptional = CategoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = UnitOfMeasureRepository.findByUom("Ounce");
        if (categoryOptional.isPresent()) {
            System.out.println(categoryOptional.get().getDescription() + " has id " + categoryOptional.get().getId());
        }
        if (unitOfMeasureOptional.isPresent()) {
            System.out.println(unitOfMeasureOptional.get().getUom() + " has id " + unitOfMeasureOptional.get().getId());
        }
        return "index";
    }
}
