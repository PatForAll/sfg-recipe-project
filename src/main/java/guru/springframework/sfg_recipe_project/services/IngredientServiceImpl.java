package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.commands.IngredientCommand;
import guru.springframework.sfg_recipe_project.converters.IngredientToIngredientCommand;
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
    public IngredientCommand findCommandByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
        //todo: error handling
        if (!recipeOptional.isPresent()) return null;
        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientOptional = recipe.getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> converter.convert(ingredient))
                .findFirst();

        return ingredientOptional.get();

    }
}
