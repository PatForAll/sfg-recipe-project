package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.domain.Recipe;
import guru.springframework.sfg_recipe_project.repositories.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/recipes")
public class RecipesController {
    private final RecipeRepository recipeRepository;

    public RecipesController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping("/list")
    public String getRecipesList(Model model) {

        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipe -> recipes.add(recipe));

        model.addAttribute("recipes", recipes);

        return "recipes/list";
    }
}
