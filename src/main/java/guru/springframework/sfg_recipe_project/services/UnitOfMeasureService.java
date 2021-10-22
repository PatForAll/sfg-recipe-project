package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUomsAsCommands();
}
