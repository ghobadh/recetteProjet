package ca.gforcesoftware.recetteprojet.bootstrap;

import ca.gforcesoftware.recetteprojet.domain.*;
import ca.gforcesoftware.recetteprojet.repositories.CategoryRepository;
import ca.gforcesoftware.recetteprojet.repositories.RecetteRepository;
import ca.gforcesoftware.recetteprojet.repositories.UnitOfMeasureRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.CategoryReactiveRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.RecetteReactiveRepository;
import ca.gforcesoftware.recetteprojet.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-11
 */
@Slf4j
@Component
public class RecetteBootStrap implements ApplicationListener<ContextRefreshedEvent> {
    //private final CategoryRepository categoryRepository;
    //private final RecetteRepository recetteRepository;
    //private final UnitOfMeasureRepository unitOfMeasureRepository;

   // @Autowired
    CategoryReactiveRepository categoryReactiveRepository;
   // @Autowired
    RecetteReactiveRepository recetteReactiveRepository;
   // @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    public RecetteBootStrap(CategoryReactiveRepository categoryReactiveRepository,RecetteReactiveRepository
            recetteReactiveRepository,UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository){
        this.categoryReactiveRepository = categoryReactiveRepository;
        this.recetteReactiveRepository = recetteReactiveRepository;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
    }


    /*public RecetteBootStrap(CategoryRepository categoryRepository, RecetteRepository recetteRepository,
                            UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recetteRepository = recetteRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;


    }*/

    @NotNull
    private List<Recette> getRecettes() {
        recetteReactiveRepository.deleteAll().block();
        unitOfMeasureReactiveRepository.deleteAll().block();
        categoryReactiveRepository.deleteAll().block();

        loadUom();
        loadCategories();
        List<Recette> recettes = new ArrayList<>(1);

        Mono<UnitOfMeasure> eachUomOptional = unitOfMeasureReactiveRepository.findByUom("Tablespoon");

        eachUomOptional.hasElement().subscribe(hasElement -> {
            if (hasElement) {
                log.info("Mono has a value.");
            } else {
                log.error("Mono is empty.");
            }
        });
        Mono <UnitOfMeasure> tableSpoonOpta = unitOfMeasureReactiveRepository.findByUom("Tablespoon");
        tableSpoonOpta.hasElement().subscribe(e -> {
            if (e) {
                log.info("Mono has a value.");
            } else {
                log.error("Mono is empty.");
            }
         });

        UnitOfMeasure eachUom = eachUomOptional.block();
        UnitOfMeasure tableSpoon = tableSpoonOpta.block();

        Mono<Category> mexicanCategoryOptional = categoryReactiveRepository.findByDescription("Mexican");
        mexicanCategoryOptional.hasElement().subscribe(e1 -> {
                if (e1) {
                    log.info("Mono has a value.");
                } else {
                    log.error("Mono is empty.");
                }
                });

        Category mexicanCategory = mexicanCategoryOptional.block();
        Recette guacoRecette = new Recette();
        guacoRecette.setDescription("Perfect Gaucamole");
        guacoRecette.setCookingTime(0);
        guacoRecette.setPrepTime(10);
        guacoRecette.setDirections("Cut the avocados:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "How to make guacamole - scoring avocado\n" +
                "Elise Bauer\n" +
                "Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. Don't overdo it! The guacamole should be a little chunky.\n" +
                "\n" +
                "How to make guacamole - smashing avocado with fork\n" +
                "Elise Bauer\n" +
                "Add the remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jicama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips");

        Notes guacoNotes = new Notes();
        guacoNotes.setRecetteNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n");

        // This is a bidirectional - we change the setNote now,
        guacoRecette.setNotes(guacoNotes);

        guacoRecette.setDifficulty(Difficulty.MEDIUM);
        guacoRecette.addIngredient(new Ingredient("ripe avacado", new BigDecimal(2), eachUom, guacoRecette.getId()));
        guacoRecette.addIngredient(new Ingredient("Kosher salt", new BigDecimal(5), tableSpoon, guacoRecette.getId()));
        guacoRecette.getCategories().add(mexicanCategory);
       // recetteRepository.save(guacoRecette);

        recettes.add(guacoRecette);
        System.out.println("Recettes found: " + recettes.size());
        return recettes;
    }
    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryReactiveRepository.save(cat1).block();

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryReactiveRepository.save(cat2).block();

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryReactiveRepository.save(cat3).block();

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryReactiveRepository.save(cat4).block();
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("Teaspoon");
        unitOfMeasureReactiveRepository.save(uom1).block();

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("Tablespoon");
        unitOfMeasureReactiveRepository.save(uom2).block();

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("Cup");
        unitOfMeasureReactiveRepository.save(uom3).block();

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setUom("Pinch");
        unitOfMeasureReactiveRepository.save(uom4).block();

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setUom("Ounce");
        unitOfMeasureReactiveRepository.save(uom5).block();

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setUom("Each");
        unitOfMeasureReactiveRepository.save(uom6).block();

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setUom("Pint");
        unitOfMeasureReactiveRepository.save(uom7).block();

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setUom("Dash");
        unitOfMeasureReactiveRepository.save(uom8).block();
    }


    /*
    Although I did not see error of Lazy initiation in here, but I added
    Transactional annotation from spring framework package (not jakarta package)
    to wrap this methode so the lazy initialize issue does not happen
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        recetteReactiveRepository.saveAll(getRecettes());
        log.debug("------ Startup is running now ----- ");
        Flux<Recette> rr = recetteReactiveRepository.findAll();

        log.info("COUNT RECETTE: {}",recetteReactiveRepository.count().block().toString());
        log.info("COUNT Category: {}",categoryReactiveRepository.count().block().toString());
        log.info("COUNT UOM: {}",unitOfMeasureReactiveRepository.count().block().toString());


    }


}
