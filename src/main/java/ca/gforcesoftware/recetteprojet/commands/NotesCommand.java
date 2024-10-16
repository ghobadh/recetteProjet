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
public class NotesCommand {
    private Long id;
    private String recetteNotes;
}