package ca.gforcesoftware.recetteprojet.converters;

import ca.gforcesoftware.recetteprojet.commands.UnitOfMeasureCommand;
import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author gavinhashemi on 2024-10-14
 */
@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureCommand convert(UnitOfMeasure source) {
        if (source == null) {
            return null;
        }
        final UnitOfMeasureCommand command = new UnitOfMeasureCommand();
        command.setDescription(source.getUom());
        command.setId(source.getId());


        return command;
    }
}
