package guru.springframework.sfg_recipe_project.commands;

import guru.springframework.sfg_recipe_project.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;
    private Recipe recipe;

    @Override
    public String toString() {
        return amount + " " + uom.getDescription() + " of " + description;
    }
}
