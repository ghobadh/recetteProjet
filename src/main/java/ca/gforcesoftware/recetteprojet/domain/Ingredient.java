package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author gavinhashemi on 2024-10-10
 */
/*
Becasue Recette is a bidirectional relationship object, when I used Lombok @Data , this causes the haschode implementation
fails due to call itself again and again until it stack overflow. Using @EqualAndHashCode , and then I exclude "recette"
variable in below, I
explicitly remove this bidirectional when Lombok tries to implement hashcode() .
 */
@Getter
@Setter
@EqualsAndHashCode(exclude = {"recette"})
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recette recette;

    @OneToOne(fetch = FetchType.EAGER)
    private UnitOfMeasure unitOfMeasure;



    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, Recette recette) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
        this.recette = recette;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure ) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }

    public Ingredient() {}


}
