package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.commands.RecipeCommand;
import guru.springframework.sfg_recipe_project.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandById(Long id);

    void deleteById(Long id);
}
