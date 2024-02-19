package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.ImageService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.DTO.DTOImage;
import hr.algebra.mvc.webshop2024.Mapper.ImageMapper;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("webShop")
public class ImageController {
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(ImageService imageService, ImageMapper imageMapper) {
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @GetMapping("admin/images/list")
    public String list(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Image>> imageFuture = CompletableFuture.supplyAsync(() -> imageService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(imageFuture);

        CompletableFuture<List<DTOImage>> realImagesFuture = allFutures.thenApply(v -> {
            List<Image> images = imageFuture.join();
            List<DTOImage> realImages = new ArrayList<>();
            for (var image : images) {
                realImages.add(imageMapper.ImageToDTOImage(image));
            }
            return realImages;
        });

        List<DTOImage> realImages = realImagesFuture.get();

        theModel.addAttribute("images", realImages);

        return "images/list-images";
    }

    @GetMapping("admin/images/showFormForAddImage")
    public String showFormForAddImage(Model theModel){
        DTOImage image =  new DTOImage();
        theModel.addAttribute("image", image);

        return "images/image-form";
    }

    @GetMapping("admin/images/showFormForUpdateImage")
    public String showFormForUpdateImage(@RequestParam("imageId") int theId, Model theModel){
        CompletableFuture<Image> imageFuture = CompletableFuture.supplyAsync(() -> imageService.findById(theId));

        Image image;
        try {
            image = imageFuture.get();
            theModel.addAttribute("image", imageMapper.ImageToDTOImage(image));
        } catch (InterruptedException | ExecutionException e) {
            return "error";
        }
        return "images/image-form";
    }

    @PostMapping("admin/images/save")
    public String saveCategory(@Valid @ModelAttribute("image") DTOImage image, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "images/image-form";
        }

        CompletableFuture<List<Image>> allDBImagesFuture = CompletableFuture.supplyAsync(imageService::findAll);

        try {
            Optional<Image> result = allDBImagesFuture.thenApply(allDBImages -> allDBImages.stream()
                    .filter(img -> image.getImageUrl().equals(img.getImageUrl()))
                    .findFirst()).get();

            if (result.isPresent()) {
                model.addAttribute("errorMessage", "Image with that URL already exists!");
                return "images/image-form";
            } else {
                CompletableFuture.runAsync(() -> imageService.save(imageMapper.DTOImageToImage(image)));
            }
        } catch (InterruptedException | ExecutionException e) {
            return "error";
        }

        return "redirect:/webShop/admin/images/list";
    }

    @GetMapping("admin/images/delete")
    public String delete(@RequestParam("imageId") int theId){
        CompletableFuture.runAsync(() -> imageService.deleteById(theId)).join();
        return "redirect:/webShop/admin/images/list";
    }

}
