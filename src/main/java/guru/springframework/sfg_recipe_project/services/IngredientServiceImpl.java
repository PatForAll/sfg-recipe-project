package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.commands.IngredientCommand;
import guru.springframework.sfg_recipe_project.converters.IngredientToIngredientCommand;
import guru.springframework.sfg_recipe_project.domain.Ingredient;
import guru.springframework.sfg_recipe_project.domain.Recipe;
import guru.springframework.sfg_recipe_project.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    IngredientToIngredientCommand converter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand converter) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
    }

    @Override
    public IngredientCommand findCommandByRecipeIdAndId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) return null;
        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient1 -> ingredient1.getId() == ingredientId)
                .findFirst();
        return converter.convert(ingredientOptional.orElse(null));

    }
}
