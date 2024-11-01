package ca.gforcesoftware.recetteprojet.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author gavinhashemi on 2024-10-14
 */
@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    /* I removed the field and defined it as string because of Mongodb
    private Long id;*/
    private String id;
    private String description;
}
