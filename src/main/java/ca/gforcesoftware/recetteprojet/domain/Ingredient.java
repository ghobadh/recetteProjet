package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

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
//@EqualsAndHashCode(exclude = {"recette"})
//@Entity
public class Ingredient {

        /* I removed the field and defined it as string because of Mongodb
    Also I commented in @Entity since it was expecting a primary key

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; */
    @Id
    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;
    private String recetteId;

    //@ManyToOne
    private Recette recette;

    //@OneToOne(fetch = FetchType.EAGER)
    @DBRef
    private UnitOfMeasure unitOfMeasure;



    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure, String recetteId) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
       this.recetteId = recetteId;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure ) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }


    public Ingredient() {}


}
