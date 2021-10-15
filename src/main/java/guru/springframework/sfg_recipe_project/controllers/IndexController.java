package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.domain.Category;
import guru.springframework.sfg_recipe_project.domain.UnitOfMeasure;
import guru.springframework.sfg_recipe_project.repositories.CategoryRepository;
import guru.springframework.sfg_recipe_project.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {
        log.debug("I'm in the Index Controller!");
        Optional<Category> categoryOptional = categoryRepository.findByCategoryName("Italian");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");

        System.out.println("Category ID for '" + categoryOptional.get().getCategoryName() + "' is: " + categoryOptional.get().getId());
        System.out.println("UOM ID for '" + unitOfMeasureOptional.get().getDescription() + "' is: " + unitOfMeasureOptional.get().getId());

        return "index";
    }
}
