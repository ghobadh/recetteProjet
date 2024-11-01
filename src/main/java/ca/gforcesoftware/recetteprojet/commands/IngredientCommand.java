package ca.gforcesoftware.recetteprojet.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author gavinhashemi on 2024-10-14
 */
@Setter
@Getter
@NoArgsConstructor
public class IngredientCommand {
    /* I removed the field and defined it as string because of Mongodb
    private Long id;*/
    private String id;
    private String recetteId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uomCommand;

}
