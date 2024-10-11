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
    private String uom;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

}
