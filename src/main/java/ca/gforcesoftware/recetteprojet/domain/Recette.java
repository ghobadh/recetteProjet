package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gavinhashemi on 2024-10-10
 */
/*
I have to change @Data to these lombok annotation the reason it the toString() method in
@Data was cause of issue since this is a bidirectional class file
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookingTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recette")
    private Set<Ingredient> ingredients = new HashSet<>();

    @Lob
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notes_id")
    private Notes notes;

    /*
    Since this is many to many we need a jointable , join columns, and inverse join column. for join column and inverse join column we use "[class name lower case]_id". In this way, then the table name is created
    it joins to id to each other. Note that, in category class file, we use only "mappedby" to tell which variable in here is mapped to
     */
    @ManyToMany
    @JoinTable(name = "recette_category",
            joinColumns = @JoinColumn(name = "recette_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
            notes.setRecette(this);
        }

    }

    public Recette addIngredient(Ingredient ingredient) {
        ingredient.setRecette(this);
        this.ingredients.add(ingredient);
        return this;
    }


}
