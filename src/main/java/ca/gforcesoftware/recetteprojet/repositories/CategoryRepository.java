package ca.gforcesoftware.recetteprojet.repositories;

import ca.gforcesoftware.recetteprojet.domain.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author gavinhashemi on 2024-10-11
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByDescription(String description);
}
