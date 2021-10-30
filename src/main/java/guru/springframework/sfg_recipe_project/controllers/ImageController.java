package guru.springframework.sfg_recipe_project.controllers;

import guru.springframework.sfg_recipe_project.commands.RecipeCommand;
import guru.springframework.sfg_recipe_project.services.ImageService;
import guru.springframework.sfg_recipe_project.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("recipes/")
public class ImageController {

    ImageService imageService;
    RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("{recipeId}/image")
    public String showUploadForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(Long.parseLong(recipeId)));

        return "recipes/imageuploadform";
    }

    @PostMapping("{recipeId}/image")
    public String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(Long.valueOf(recipeId), file);

        return String.format("redirect:/recipes/%s/show", recipeId);
    }

    @GetMapping("{recipeId}/recipeimage")
    public void renderImageFromDB(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeCommand command = recipeService.findCommandById(Long.parseLong(recipeId));

        byte[] byteArray = new byte[command.getImage().length];
        int i = 0;
        for (Byte b : command.getImage()) {
            byteArray[i++] = b;
        }

        response.setContentType("image/jpeg");
        InputStream is = new ByteArrayInputStream(byteArray);
        IOUtils.copy(is, response.getOutputStream());
    }
}
