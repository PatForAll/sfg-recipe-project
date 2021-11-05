package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.commands.RecipeCommand;
import guru.springframework.sfg_recipe_project.domain.Recipe;
import guru.springframework.sfg_recipe_project.exceptions.NotFoundException;
import guru.springframework.sfg_recipe_project.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RecipesControllerTest {

    RecipesController recipesController;
    MockMvc mockMvc;

    @Mock
    Model model;
    @Mock
    RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recipesController = new RecipesController(recipeService);

        mockMvc = MockMvcBuilders.standaloneSetup(recipesController)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipesController).build();

        mockMvc.perform(get("/recipes/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/list"));
    }

    @Test
    void getRecipesList() {
        //given
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(7L);
        recipes.add(recipe);
        when(recipeService.getRecipes()).thenReturn(recipes);

        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        //when
        String result = recipesController.getRecipesList(model);

        assertEquals("recipes/list", result);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        assertEquals(2, setInController.size());
    }

    @Test
    void getRecipe() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipes/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/show"));
    }

    @Test
    public void getRecipeNotFound() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        when(recipeService.findById(anyLong())).thenThrow(NotFoundException.class);

        mockMvc.perform(get("/recipes/1/show"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void getBadRequest() throws Exception {
        mockMvc.perform(get("/recipes/one/show"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400error"));
    }

    @Test
    void getNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();

        mockMvc.perform(get("/recipes/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void postNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.saveRecipeCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("description", "Nice stuff.")
                        .param("directions", "Cook it."))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/2/show"));
    }

    @Test
    void postNewRecipeFormValidationFailed() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        mockMvc.perform(post("/recipes")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", ""))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"))
                .andExpect(view().name("recipes/recipeForm"));
    }

    @Test
    void getUpdateView() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId(2L);

        when(recipeService.findCommandById(anyLong())).thenReturn(command);

        mockMvc.perform(get("/recipes/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipes/recipeForm"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void deleteAction() throws Exception {
        mockMvc.perform(get("/recipes/2/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipes/list"));

        verify(recipeService).deleteById(anyLong());
    }
}