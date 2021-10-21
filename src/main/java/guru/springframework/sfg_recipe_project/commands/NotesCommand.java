package guru.springframework.sfg_recipe_project.commands;

import guru.springframework.sfg_recipe_project.domain.Recipe;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
    private Long id;
    private Recipe recipe;
    private String recipeNotes;
}
