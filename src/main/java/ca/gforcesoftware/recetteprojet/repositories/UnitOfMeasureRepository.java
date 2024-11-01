package ca.gforcesoftware.recetteprojet.repositories;

import ca.gforcesoftware.recetteprojet.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-11
 */
public interface UnitOfMeasureRepository extends MongoRepository<UnitOfMeasure, String> {
    Optional<UnitOfMeasure> findByUom(String uom);
}
