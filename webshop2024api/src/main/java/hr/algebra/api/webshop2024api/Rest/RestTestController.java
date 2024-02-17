package hr.algebra.api.webshop2024api.Rest;

import hr.algebra.bl.webshop2024bl.Service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import hr.algebra.dal.webshop2024dal.Entity.Image;

@RestController
@RequestMapping("/webshopApi")
public class RestTestController {
    @Autowired
    private final ImageService imageService;

    public RestTestController(ImageService imageService) {
        this.imageService = imageService;
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/images/list")
    public CompletableFuture<List<Image>> findAllCerts(){
        List<Image> images = imageService.findAll();

        return CompletableFuture.completedFuture(images);
    }

    @GetMapping("/images/test")
    public String returnString(){
        System.out.printf("sdfsdfdsssdfsd1000");
        return "Hello";
    }
}

