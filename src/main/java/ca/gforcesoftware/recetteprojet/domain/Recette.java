package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

/**
 * @author gavinhashemi on 2024-10-10
 */
/*
I have to change @Data to these lombok annotation the reason it the toString() method in
@Data was cause of issue since this is a bidirectional class file
 */
@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
@Document
public class Recette {

    /* I removed the field and defined it as string because of Mongodb
    Also I commented in @Entity since it was expecting a primary key

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     */
    @Id
    private String id = UUID.randomUUID().toString();

    private String description;
    private Integer prepTime;
    private Integer cookingTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Byte[] image;
    private Notes notes;

    @DBRef
    private List<Category> categories = new ArrayList<>();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
           // notes.setRecette(this);
        }

    }

    public Recette addIngredient(Ingredient ingredient) {
       // ingredient.setRecette(this);
        this.ingredients.add(ingredient);
        return this;
    }

    public Recette deleteIngredient(Long ingredientId) {
        this.ingredients.remove(ingredientId);
        return this;
    }

}
