package ca.gforcesoftware.recetteprojet.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author gavinhashemi on 2024-10-10
 */
//@Data
//@Entity
@Getter
@Setter
@Document
public class UnitOfMeasure {

    //@GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private String id;
    private String uom;


}
