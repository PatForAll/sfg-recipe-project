package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
