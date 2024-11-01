package ca.gforcesoftware.recetteprojet.repositories;

import ca.gforcesoftware.recetteprojet.domain.Recette;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

/**
 * @author gavinhashemi on 2024-10-11
 */

public interface RecetteRepository extends MongoRepository<Recette, String> {
}
