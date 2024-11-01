package ca.gforcesoftware.recetteprojet.repositories;

import ca.gforcesoftware.recetteprojet.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-11
 */
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByDescription(String description);
}
