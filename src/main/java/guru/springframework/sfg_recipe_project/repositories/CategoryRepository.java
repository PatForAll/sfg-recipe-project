package guru.springframework.sfg_recipe_project.repositories;

import guru.springframework.sfg_recipe_project.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
