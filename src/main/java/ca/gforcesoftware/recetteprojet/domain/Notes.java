package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author gavinhashemi on 2024-10-10
 */
@Data
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recette recette;
    @Lob
    private String RecetteNotes;

    public String getRecetteNotes() {
        return RecetteNotes;
    }

    public void setRecetteNotes(String recetteNotes) {
        RecetteNotes = recetteNotes;
    }
}
