package guru.springframework.sfg_recipe_project;

import guru.springframework.sfg_recipe_project.domain.*;
import guru.springframework.sfg_recipe_project.repositories.CategoryRepository;
import guru.springframework.sfg_recipe_project.repositories.RecipeRepository;
import guru.springframework.sfg_recipe_project.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;

    public DataLoader(UnitOfMeasureRepository unitOfMeasureRepository, CategoryRepository categoryRepository, RecipeRepository recipeRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Optional<UnitOfMeasure> pieces = unitOfMeasureRepository.findByDescription("Pieces");

        Ingredient avocados = new Ingredient();
        avocados.setAmount(BigDecimal.valueOf(2));
        avocados.setDescription("ripe");
        pieces.ifPresent(avocados::setUom);

        Optional<Category> mexican = categoryRepository.findByCategoryName("Mexican");

        Recipe bestGuacamole = new Recipe();
        mexican.ifPresent(category -> bestGuacamole.getCategories().add(category));
        bestGuacamole.setPrepTime(10);
        bestGuacamole.setCookTime(10);
        bestGuacamole.setDescription("The best guacamole.");
        bestGuacamole.setDifficulty(Difficulty.EASY);
        bestGuacamole.setDirections("Cut the avocado:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife " +
                "and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.");
        bestGuacamole.addIngredient(avocados);
        bestGuacamole.setServings(4);
        bestGuacamole.setSource("Simply Recipes");
        bestGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        log.debug("Add " + bestGuacamole.getDescription() + " to repository.");
        recipeRepository.save(bestGuacamole);

    }
}
