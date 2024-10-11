package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @author gavinhashemi on 2024-10-10
 */
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

    public Ingredient() {

    }

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amoout) {
        this.amount = amoout;
    }

    public Recette getRecette() {
        return recette;
    }

    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }
}
