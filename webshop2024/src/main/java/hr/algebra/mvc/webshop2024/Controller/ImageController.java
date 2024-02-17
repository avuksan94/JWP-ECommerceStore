package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.ImageService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.DTO.DTOImage;
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

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("admin/images/list")
    public String list(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Image>> imageFuture = CompletableFuture.supplyAsync(() -> imageService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(imageFuture);

        CompletableFuture<List<DTOImage>> realImagesFuture = allFutures.thenApply(v -> {
            List<Image> images = imageFuture.join();
            List<DTOImage> realImages = new ArrayList<>();
            for (var image : images) {
                DTOImage img = new DTOImage();
                img.setImageId(image.getImageId());
                img.setImageUrl(image.getImageUrl());

                realImages.add(img);
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
        Image image = imageService.findById(theId);

        DTOImage dtoImage = new DTOImage();
        dtoImage.setImageId(image.getImageId());
        dtoImage.setImageUrl(image.getImageUrl());

        theModel.addAttribute("image", dtoImage);

        //send over to our form
        return "images/image-form";
    }

    @PostMapping("admin/images/save")
    public String saveCategory(@Valid @ModelAttribute("image") DTOImage image, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "images/image-form";
        }

        List<Image> allDBImages = imageService.findAll();

        Optional<Image> result = allDBImages.stream()
                .filter(imageRes -> image.getImageUrl().equals(imageRes.getImageUrl()))
                .findFirst();

        if(result.isPresent()){
            model.addAttribute("errorMessage", "Image with that URL already exists!");
            return "images/image-form";
        }

        Image imageToSave = new Image();
        imageToSave.setImageId(image.getImageId());
        imageToSave.setImageUrl(image.getImageUrl());
        imageService.save(imageToSave);

        return "redirect:/webShop/admin/images/list";
    }

    @GetMapping("admin/images/delete")
    public String delete(@RequestParam("imageId") int theId){
        imageService.deleteById(theId);

        return "redirect:/webShop/admin/images/list";
    }

}
