package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class RecipesController {

    private final RecipeService recipeService;

    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/list")
    public String getRecipesList(Model model) {

        log.debug("I'm in the recipes list controller!");
        model.addAttribute("recipes", recipeService.getRecipes());

        return "recipes/list";
    }

    @RequestMapping("/show/{id}")
    public String showById(Model model, @PathVariable String id) {
        model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
        return "recipes/show";
    }
}
