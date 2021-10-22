package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.commands.IngredientCommand;
import guru.springframework.sfg_recipe_project.converters.IngredientCommandToIngredient;
import guru.springframework.sfg_recipe_project.converters.IngredientToIngredientCommand;
import guru.springframework.sfg_recipe_project.domain.Ingredient;
import guru.springframework.sfg_recipe_project.domain.Recipe;
import guru.springframework.sfg_recipe_project.repositories.RecipeRepository;
import guru.springframework.sfg_recipe_project.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    RecipeRepository recipeRepository;
    UnitOfMeasureRepository unitOfMeasureRepository;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    IngredientCommandToIngredient ingredientCommandToIngredient;

    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
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
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                .findFirst();

        return ingredientOptional.orElse(null);

    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        //todo: error handling
        if (!recipeOptional.isPresent()) return new IngredientCommand();
        Recipe recipe = recipeOptional.get();
        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(command.getId()))
                .findFirst();
        if (ingredientOptional.isPresent()) {
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setAmount(command.getAmount());
            ingredientFound.setUom(unitOfMeasureRepository
                    .findById(command.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("UOM not found!")));
            ingredientFound.setDescription(command.getDescription());

            Recipe savedRecipe = recipeRepository.save(recipe);

            //todo: error handling
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst()
                    .get());
        }

        Set<Long> ingredientIdSet = new HashSet<>();
        recipe.getIngredients().stream().map(ingredient -> ingredientIdSet.add(ingredient.getId()));
        //todo: error handling
        recipe.addIngredient(ingredientCommandToIngredient.convert(command));

        Recipe savedRecipe = recipeRepository.save(recipe);

        //todo: error handling
        return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                .filter(recipeIngredients -> !ingredientIdSet.contains(recipeIngredients.getId()))
                .findFirst()
                .get());
    }
}
