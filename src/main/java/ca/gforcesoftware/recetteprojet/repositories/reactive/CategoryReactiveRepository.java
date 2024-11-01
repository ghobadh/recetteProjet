package ca.gforcesoftware.recetteprojet.repositories.reactive;

import ca.gforcesoftware.recetteprojet.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-11-01
 */
public interface CategoryReactiveRepository extends ReactiveMongoRepository<Category, String> {
    Mono<Category> findByDescription(String description);
}
