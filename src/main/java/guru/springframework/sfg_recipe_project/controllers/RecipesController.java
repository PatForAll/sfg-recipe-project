package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/recipes")
public class RecipesController {

    private final RecipeService recipeService;

    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/list")
    public String getRecipesList(Model model) {

        model.addAttribute("recipes", recipeService.getRecipes());

        return "recipes/list";
    }
}
