package ca.gforcesoftware.recetteprojet.repositories;

import ca.gforcesoftware.recetteprojet.domain.Category;
import org.springframework.data.repository.CrudRepository;

/**
 * @author gavinhashemi on 2024-10-11
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
