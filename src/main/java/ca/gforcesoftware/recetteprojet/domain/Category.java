package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-11
 */
/*
Becasue Recette is a bidirectional relationship object, when I used Lombok @Data , this causes the haschode implementation
fails due to call itself again and again until it stack overflow. Using @EqualAndHashCode a, and then I exclude "recettes"
variable in below, I
explicitly remove this bidirectional when Lombok tries to implement hashcode() .
 */
@Data
@EqualsAndHashCode(exclude = {"recettes"})
@Entity
@Table(name="category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    //This is the name of the variable in Recette class file ---> private Set<Category> categories;
    @ManyToMany(mappedBy = "categories")
    private Set<Recette> recettes;

    public Set<Recette> getRecette() {
        return recettes;
    }

    public void setRecette(Set<Recette> recettes) {
        this.recettes = recettes;


    }
}
