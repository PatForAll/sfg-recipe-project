package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RecipesControllerTest {

    RecipesController recipesController;

    @Mock
    Model model;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipesController = new RecipesController(recipeService);
    }

    @Test
    void getRecipesList() {
        String result = recipesController.getRecipesList(model);

        assertEquals("recipes/list", result);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
    }
}