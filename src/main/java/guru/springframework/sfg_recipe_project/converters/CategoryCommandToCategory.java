package guru.springframework.sfg_recipe_project.converters;

import guru.springframework.sfg_recipe_project.commands.CategoryCommand;
import guru.springframework.sfg_recipe_project.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(CategoryCommand source) {
        if (source == null) return null;

        final Category cat = new Category();
        cat.setId(source.getId());
        cat.setCategoryName(source.getCategoryName());
        return cat;
    }
}
