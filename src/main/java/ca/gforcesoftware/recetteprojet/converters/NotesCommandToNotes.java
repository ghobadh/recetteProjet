package ca.gforcesoftware.recetteprojet.converters;

import ca.gforcesoftware.recetteprojet.commands.NotesCommand;
import ca.gforcesoftware.recetteprojet.domain.Notes;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author gavinhashemi on 2024-10-14
 */
@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(NotesCommand source) {
        if (source == null) {
            return null;
        }
        Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecetteNotes(source.getRecetteNotes());

        return notes;
    }
}
