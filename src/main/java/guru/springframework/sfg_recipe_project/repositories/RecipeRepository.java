package guru.springframework.sfg_recipe_project.repositories;

import guru.springframework.sfg_recipe_project.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
