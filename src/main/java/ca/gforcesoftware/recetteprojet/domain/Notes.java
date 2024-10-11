package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;

/**
 * @author gavinhashemi on 2024-10-10
 */
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Recette recette;
    @Lob
    private String RecetteNotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public String getRecetteNotes() {
        return RecetteNotes;
    }

    public void setRecetteNotes(String recetteNotes) {
        RecetteNotes = recetteNotes;
    }
}
