package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;

import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-11
 */
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Recette> getRecette() {
        return recettes;
    }

    public void setRecette(Set<Recette> recettes) {
        this.recettes = recettes;


    }
}
