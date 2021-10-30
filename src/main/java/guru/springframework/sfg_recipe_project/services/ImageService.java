package guru.springframework.sfg_recipe_project.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile imageFile);
}
