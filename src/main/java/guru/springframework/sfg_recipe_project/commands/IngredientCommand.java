package guru.springframework.sfg_recipe_project.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Long id;
    private Long recipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand uom;

    @Override
    public String toString() {
        if (uom != null && uom.getDescription() != null)
            return amount + " " + uom.getDescription() + " of " + description;
        return null;
    }
}
