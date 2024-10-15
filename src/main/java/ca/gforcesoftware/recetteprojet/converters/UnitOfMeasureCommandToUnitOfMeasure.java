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
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
         if(source == null) {
             return null;
         }
         final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
         unitOfMeasure.setId(source.getId());
         unitOfMeasure.setUom(source.getDescription());
         return unitOfMeasure;
    }
}
