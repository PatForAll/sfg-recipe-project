package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.commands.IngredientCommand;
import guru.springframework.sfg_recipe_project.services.IngredientService;
import guru.springframework.sfg_recipe_project.services.RecipeService;
import guru.springframework.sfg_recipe_project.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipes")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping
    @RequestMapping("/{recipeId}/ingredients/list")
    public String listIngredients(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));
        return "recipes/ingredients/list";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredients/{ingredientId}/show")
    public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findCommandByRecipeIdAndIngredientId(Long.parseLong(recipeId), Long.parseLong(ingredientId)));
        return "recipes/ingredients/show";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredients/new")
    public String newIngredient(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipeId", recipeId);
        model.addAttribute("ingredient", new IngredientCommand());

        return "recipes/ingredients/ingredientform";
    }

    @GetMapping
    @RequestMapping("/{recipeId}/ingredients/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient",
                ingredientService.findCommandByRecipeIdAndIngredientId(Long.parseLong(recipeId),
                        Long.parseLong(ingredientId)));
        model.addAttribute("uomList", unitOfMeasureService.listAllUomsAsCommands());

        return "recipes/ingredients/ingredientform";
    }

    @PostMapping
    @RequestMapping("/{recipeId}/ingredients")
    public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        log.debug("saved receipeId: " + savedCommand.getRecipeId());
        log.debug("saved ingredientId: " + savedCommand.getId());

        return "redirect:/recipes/" + savedCommand.getRecipeId() + "/show";
    }
}
