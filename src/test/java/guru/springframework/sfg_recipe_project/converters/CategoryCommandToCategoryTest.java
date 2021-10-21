package guru.springframework.sfg_recipe_project.converters;

import guru.springframework.sfg_recipe_project.commands.CategoryCommand;
import guru.springframework.sfg_recipe_project.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CategoryCommandToCategoryTest {

    public static final Long LONG_ID = new Long(1L);
    public static final String CATEGORY_NAME = "category-name";

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void convert() {
        CategoryCommand command = new CategoryCommand();
        command.setId(LONG_ID);
        command.setCategoryName(CATEGORY_NAME);

        Category cat = converter.convert(command);

        assertNotNull(cat);
        assertEquals(LONG_ID, cat.getId());
        assertEquals(CATEGORY_NAME, cat.getCategoryName());
    }
}