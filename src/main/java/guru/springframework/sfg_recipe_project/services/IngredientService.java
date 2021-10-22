package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findCommandByRecipeIdAndId(Long recipeId, Long ingredientId);
}
