package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author gavinhashemi on 2024-10-10
 */
/*
Becasue Recette is a bidirectional relationship object, when I used Lombok @Data , this causes the haschode implementation
fails due to call itself again and again until it stack overflow. Using @EqualAndHashCode annotation, and then I exclude "recette"
variable in below, I
explicitly remove this bidirectional when Lombok tries to implement hashcode() .
 */
@Setter
@Getter
//@EqualsAndHashCode(exclude = {"recette"})
//@Entity
public class Notes {

        /* I removed the field and defined it as string because of Mongodb
    Also I commented in @Entity since it was expecting a primary key

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; */
    @Id
    private String id;

    //@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
   // @JoinColumn(name = "recette_id")
    private Recette recette;
    //@Lob
    private String RecetteNotes;
}
