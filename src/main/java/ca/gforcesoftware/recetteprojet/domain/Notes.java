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
@Data
@EqualsAndHashCode(exclude = {"recette"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recette recette;
    @Lob
    private String RecetteNotes;
}
