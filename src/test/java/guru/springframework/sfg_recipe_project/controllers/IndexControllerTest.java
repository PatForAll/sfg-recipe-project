package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.repositories.CategoryRepository;
import guru.springframework.sfg_recipe_project.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class IndexControllerTest {

    IndexController indexController;

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        indexController = new IndexController(categoryRepository, unitOfMeasureRepository);
    }

    @Test
    void getIndexPage() {
        String result = indexController.getIndexPage();

        Assertions.assertEquals("index", result);
        verify(categoryRepository, times(1)).findByCategoryName(any());
        verify(unitOfMeasureRepository, times(1)).findByDescription(any());
    }
}