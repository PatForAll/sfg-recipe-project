package guru.springframework.sfg_recipe_project.services;

import guru.springframework.sfg_recipe_project.domain.Recipe;
import guru.springframework.sfg_recipe_project.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile imageFile) {
        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            if (!recipeOptional.isPresent()) throw new IOException("No Recipe found!");
            Recipe recipe = recipeOptional.get();

            Byte[] bytes = new Byte[imageFile.getBytes().length];
            int i = 0;

            for (byte b : imageFile.getBytes()) {
                bytes[i++] = b;
            }
            recipe.setImage(bytes);
            recipeRepository.save(recipe);
        } catch (IOException exception) {
            log.error("Error occurred", exception);
            exception.printStackTrace();
        }
    }
}
