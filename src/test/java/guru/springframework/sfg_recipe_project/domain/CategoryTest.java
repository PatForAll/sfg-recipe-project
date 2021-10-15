package guru.springframework.sfg_recipe_project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoryTest {

    Category category;

    @BeforeEach
    public void setUp() {
        category = new Category();
    }

    @Test
    void getId() {
        Long idValue = 7l;
        category.setId(idValue);

        assertEquals(idValue, category.getId());
    }

    @Test
    void getCategoryName() {
        String categoryName = "Test";
        category.setCategoryName(categoryName);
        assertEquals(categoryName, category.getCategoryName());
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        recipe.setDescription("TestDescription");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeSet.add(recipe);
        category.setRecipes(recipeSet);

        assertEquals(recipeSet, category.getRecipes());
    }
}