package hr.algebra.api.webshop2024api.Rest;

import hr.algebra.api.webshop2024api.ApiDTO.DTOCategory;
import hr.algebra.api.webshop2024api.ApiDTO.DTOImage;
import hr.algebra.api.webshop2024api.ApiDTO.DTOProductImage;
import hr.algebra.api.webshop2024api.ApiMapper.ImageMapper;
import hr.algebra.api.webshop2024api.ApiMapper.ProductImageMapper;
import hr.algebra.api.webshop2024api.ApiMapper.ProductMapper;
import hr.algebra.bl.webshop2024bl.Service.ProductImageService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.ProductImage;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webShopApi")
public class ProductImageController {
    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;
    private final ImageMapper imageMapper;
    private final ProductMapper productMapper;

    public ProductImageController(ProductImageService productImageService, ProductImageMapper productImageMapper, ImageMapper imageMapper, ProductMapper productMapper) {
        this.productImageService = productImageService;
        this.productImageMapper = productImageMapper;
        this.imageMapper = imageMapper;
        this.productMapper = productMapper;
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/productImages/allProductImages")
    public CompletableFuture<List<DTOProductImage>> findAllProductImages(){
        return CompletableFuture.completedFuture(
                productImageService.findAll()
                        .stream()
                        .map(productImageMapper::ProductImageToDTOProductImage)
                        .collect(Collectors.toList())
        );
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/productImages/{productImageId}")
    public CompletableFuture<DTOProductImage> findProductImageById(@PathVariable int productImageId) {
        ProductImage productImage = productImageService.findById(productImageId);
        if (productImage == null) {
            throw new CustomNotFoundException("Product Image not found for id: " + productImageId);
        }
        return CompletableFuture.completedFuture(productImageMapper.ProductImageToDTOProductImage(productImage));
    }

    @Async
    @ResponseBody
    @PostMapping("/productImages")
    public CompletableFuture<DTOProductImage> createProductImage(@Valid @RequestBody DTOProductImage dtoProductImage) {
        ProductImage newProductImage = productImageMapper.DTOProductImageToProductImage(dtoProductImage);
        ProductImage savedProductImage = productImageService.save(newProductImage);
        return CompletableFuture.supplyAsync(() -> productImageMapper.ProductImageToDTOProductImage(savedProductImage))
                .thenApplyAsync(savedDtoProductImage -> savedDtoProductImage);
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/productImages/{productImageId}")
    public CompletableFuture<DTOProductImage> updateProductImage(@PathVariable int productImageId, @Valid @RequestBody DTOProductImage productImage){
        ProductImage extistingProductImage = productImageService.findById(productImageId);

        if (extistingProductImage == null) {
            throw new CustomNotFoundException("Product image not found for id: " + productImageId);
        }

        extistingProductImage.setImage(imageMapper.DTOImageToImage(productImage.getImage()));
        extistingProductImage.setProduct(productMapper.DTOProductToProduct(productImage.getProduct()));

        ProductImage updatedProductImage = productImageService.save(extistingProductImage);

        return CompletableFuture.completedFuture(productImageMapper.ProductImageToDTOProductImage(updatedProductImage));
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/productImages/{productImageId}")
    public CompletableFuture<String> deleteProductImage(@PathVariable int productImageId){
        ProductImage productImageToDelete = productImageService.findById(productImageId);
        if (productImageToDelete == null){
            throw new CustomNotFoundException(("Product image id not found - " + productImageId));
        }
        productImageService.deleteById(productImageId);

        return CompletableFuture.completedFuture("Deleted Product image with ID: " + productImageId);
    }
}
