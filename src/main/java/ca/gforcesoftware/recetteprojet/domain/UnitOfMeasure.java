package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;

/**
 * @author gavinhashemi on 2024-10-10
 */
@Entity
public class UnitOfMeasure {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String uom) {
        this.description = uom;
    }

}
