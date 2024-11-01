package ca.gforcesoftware.recetteprojet.repositories.reactive;

import ca.gforcesoftware.recetteprojet.domain.Recette;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author gavinhashemi on 2024-11-01
 */
public interface RecetteReactiveRepository extends ReactiveMongoRepository<Recette, String> {
}
