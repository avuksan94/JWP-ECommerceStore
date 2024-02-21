package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.ImageService;
import hr.algebra.bl.webshop2024bl.Service.ProductImageService;
import hr.algebra.bl.webshop2024bl.Service.ProductService;
import hr.algebra.bl.webshop2024bl.Service.SubcategoryService;
import hr.algebra.dal.webshop2024dal.Consts.WebShopConsts;
import hr.algebra.dal.webshop2024dal.Entity.Image;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ProductImage;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
import hr.algebra.mvc.webshop2024.ViewModel.ProductVM;
import jakarta.validation.Valid;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
@RequestMapping("webShop")
public class ProductController {
    private final ProductService productService;
    private final ProductImageService productImageService;
    private final SubcategoryService subcategoryService;
    private final ImageService imageService;

    public ProductController(ProductService productService, ProductImageService productImageService, SubcategoryService subcategoryService, ImageService imageService) {
        this.productService = productService;
        this.productImageService = productImageService;
        this.subcategoryService = subcategoryService;
        this.imageService = imageService;
    }

    @GetMapping("products/list")
    public String list(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> productsFuture = CompletableFuture.supplyAsync(() -> productService.findAll());
        CompletableFuture<List<ProductImage>> productImagesFuture = CompletableFuture.supplyAsync(() -> productImageService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(productsFuture, productImagesFuture);

        CompletableFuture<List<ProductVM>> realProductsFuture = allFutures.thenApply(v -> {
            List<Product> products = productsFuture.join();
            List<ProductImage> productImages = productImagesFuture.join();
            List<ProductVM> realProducts = new ArrayList<>();
            for (var product : products) {
                ProductVM prod = new ProductVM();
                String imageLink = WebShopConsts.DEFAULT_IMAGE_FILENAME;
                prod.setProductId(product.getProductId());
                prod.setName(product.getName());
                prod.setDescription(product.getDescription());
                prod.setPrice(product.getPrice());
                prod.setSubcategoryId(product.getSubcategory().getSubcategoryId());

                for (var productImage : productImages) {
                    if (Objects.equals(productImage.getProduct().getProductId(), product.getProductId())) {
                        imageLink = productImage.getImage().getImageUrl();
                        break;
                    }
                }

                prod.setImageUrls(imageLink);
                realProducts.add(prod);
            }
            return realProducts;
        });

        List<ProductVM> realProducts = realProductsFuture.get();

        theModel.addAttribute("products", realProducts);

        return "products/list-products";
    }

    @GetMapping("/products/showSelectedProduct")
    public String showSelected(@RequestParam("productId") int theId, Model theModel){
        CompletableFuture<Product> productFuture = CompletableFuture.supplyAsync(() -> productService.findById(theId));
        CompletableFuture<List<ProductImage>> imagesFuture = CompletableFuture.supplyAsync(() -> productImageService.findAll());

        CompletableFuture.allOf(productFuture, imagesFuture).join();

        try {
            Product product = productFuture.get();

            List<ProductImage> images = imagesFuture.get();
            Image image = images.stream()
                    .filter(productImage -> Objects.equals(productImage.getProduct().getProductId(), product.getProductId()))
                    .findFirst()
                    .map(ProductImage::getImage)
                    .orElse(new Image());

            theModel.addAttribute("image", image);
            theModel.addAttribute("product", product);
        } catch (InterruptedException | ExecutionException e) {
            return "error";
        }

        return "products/product-display";
    }

    //fix
    @GetMapping("products/findByKeyword")
    public String findByKeyword(Model theModel, String keyword) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> productsFuture = CompletableFuture.supplyAsync(() -> {
            if (keyword != null && !keyword.trim().isEmpty()) {
                return productService.findByKeyword(keyword.trim());
            } else {
                return productService.findAll();
            }
        });

        CompletableFuture<List<ProductImage>> productImagesFuture = CompletableFuture.supplyAsync(() ->
                productImageService.findAll()
        );

        CompletableFuture<List<ProductVM>> realProductsFuture = productsFuture.thenCombine(productImagesFuture, (products, productImages) -> {
            List<ProductVM> realProducts = new ArrayList<>();
            for (var product : products) {
                ProductVM prod = new ProductVM();
                String imageLink = WebShopConsts.DEFAULT_IMAGE_FILENAME; // Default image link
                prod.setProductId(product.getProductId());
                prod.setName(product.getName());
                prod.setDescription(product.getDescription());
                prod.setPrice(product.getPrice());
                prod.setSubcategoryId(product.getSubcategory().getSubcategoryId());

                for (var productImage : productImages) {
                    if (Objects.equals(productImage.getProduct().getProductId(), product.getProductId())) {
                        imageLink = productImage.getImage().getImageUrl();
                        break;
                    }
                }

                prod.setImageUrls(imageLink);
                realProducts.add(prod);
            }
            return realProducts;
        });
        List<ProductVM> realProducts = realProductsFuture.get();

        theModel.addAttribute("products", realProducts);

        return "products/list-products";
    }

    @GetMapping("admin/products/findByKeyword")
    public String findByKeywordAdmin(Model theModel, String keyword) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> productsFuture = CompletableFuture.supplyAsync(() -> {
            if (keyword != null && !keyword.trim().isEmpty()) {
                return productService.findByKeyword(keyword.trim());
            } else {
                return productService.findAll();
            }
        });

        CompletableFuture<List<ProductImage>> productImagesFuture = CompletableFuture.supplyAsync(() ->
                productImageService.findAll()
        );

        CompletableFuture<List<ProductVM>> realProductsFuture = productsFuture.thenCombine(productImagesFuture, (products, productImages) -> {
            List<ProductVM> realProducts = new ArrayList<>();
            for (var product : products) {
                ProductVM prod = new ProductVM();
                String imageLink = WebShopConsts.DEFAULT_IMAGE_FILENAME; // Default image link
                prod.setProductId(product.getProductId());
                prod.setName(product.getName());
                prod.setDescription(product.getDescription());
                prod.setPrice(product.getPrice());
                prod.setSubcategoryId(product.getSubcategory().getSubcategoryId());

                for (var productImage : productImages) {
                    if (Objects.equals(productImage.getProduct().getProductId(), product.getProductId())) {
                        imageLink = productImage.getImage().getImageUrl();
                        break;
                    }
                }

                prod.setImageUrls(imageLink);
                realProducts.add(prod);
            }
            return realProducts;
        });

        // wait for the future to complete and get the result
        List<ProductVM> realProducts = realProductsFuture.get();

        theModel.addAttribute("products", realProducts);

        return "products/list-products-admin";
    }

    //FOR ADMIN
    @GetMapping("admin/products/list")
    public String listAdmin(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Product>> productsFuture = CompletableFuture.supplyAsync(() -> productService.findAll());
        CompletableFuture<List<ProductImage>> productImagesFuture = CompletableFuture.supplyAsync(() -> productImageService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(productsFuture, productImagesFuture);

        CompletableFuture<List<ProductVM>> realProductsFuture = allFutures.thenApply(v -> {
            List<Product> products = productsFuture.join();
            List<ProductImage> productImages = productImagesFuture.join();
            List<ProductVM> realProducts = new ArrayList<>();
            for (var product : products) {
                ProductVM prod = new ProductVM();
                String imageLink = WebShopConsts.DEFAULT_IMAGE_FILENAME;
                prod.setProductId(product.getProductId());
                prod.setName(product.getName());
                prod.setDescription(product.getDescription());
                prod.setPrice(product.getPrice());
                prod.setSubcategoryId(product.getSubcategory().getSubcategoryId());

                for (var productImage : productImages) {
                    if (Objects.equals(productImage.getProduct().getProductId(), product.getProductId())) {
                        imageLink = productImage.getImage().getImageUrl();
                        break;
                    }
                }

                prod.setImageUrls(imageLink);
                realProducts.add(prod);
            }
            return realProducts;
        });

        List<ProductVM> realProducts = realProductsFuture.get();

        theModel.addAttribute("products", realProducts);

        return "products/list-products-admin";
    }

    @GetMapping("admin/products/showFormForAddProduct")
    public String showFormForAddVideo(Model theModel){

        //create the model attribute to bind form data
        ProductVM product = new ProductVM();
        theModel.addAttribute("product", product);

        List<Subcategory> subcategories = subcategoryService.findAll();
        theModel.addAttribute("subcategories", subcategories);

        List<Image> images = imageService.findAll();
        theModel.addAttribute("images", images);

        return "products/product-form";
    }

    @GetMapping("admin/products/showFormForUpdateProduct")
    public String showFormForUpdateProduct(@RequestParam("productId") int theId,Model theModel){
        CompletableFuture<Product> productFuture = CompletableFuture.supplyAsync(() -> productService.findById(theId));
        CompletableFuture<List<Subcategory>> subcategoriesFuture = CompletableFuture.supplyAsync(() -> subcategoryService.findAll());
        CompletableFuture<List<Image>> imagesFuture = CompletableFuture.supplyAsync(() -> imageService.findAll());
        CompletableFuture<List<ProductImage>> allProductImagesFuture = CompletableFuture.supplyAsync(() -> productImageService.findAll());

        CompletableFuture.allOf(productFuture, subcategoriesFuture, imagesFuture, allProductImagesFuture).join();

        Product product;
        try {
            product = productFuture.get();
            if (product == null) {
                return "redirect:/webShop/admin/products/list";
            }

            List<Subcategory> subcategories = subcategoriesFuture.get();
            List<Image> images = imagesFuture.get();
            List<ProductImage> allProductImages = allProductImagesFuture.get();

            List<ProductImage> productImages = allProductImages.stream()
                    .filter(image -> image.getProduct().getProductId() == theId)
                    .collect(Collectors.toList());

            ProductVM productModel = new ProductVM();
            productModel.setProductId(product.getProductId());
            productModel.setName(product.getName());
            productModel.setDescription(product.getDescription());
            productModel.setPrice(product.getPrice());
            productModel.setSubcategoryId(product.getSubcategory().getSubcategoryId());

            // Setting a default image if no images are associated with the product
            String imageLink = productImages.isEmpty() ? WebShopConsts.DEFAULT_IMAGE_FILENAME
                    : productImages.get(0).getImage().getImageUrl();

            productModel.setImageUrls(imageLink);
            productModel.setSelectedImageId(productImages.isEmpty() ? null : productImages.get(0).getImage().getImageId());

            theModel.addAttribute("product", productModel);
            theModel.addAttribute("subcategories", subcategories);
            theModel.addAttribute("images", images);
        } catch (InterruptedException | ExecutionException e) {
            return "error";
        }

        return "products/product-form-update";
    }

    @PostMapping("admin/products/save")
    public String saveProduct(@Valid @ModelAttribute("product") ProductVM product, BindingResult bindingResult, Model model) {
        CompletableFuture<List<Subcategory>> subcategoriesFuture = CompletableFuture.supplyAsync(subcategoryService::findAll);
        CompletableFuture<List<Image>> imagesFuture = CompletableFuture.supplyAsync(imageService::findAll);

        CompletableFuture.allOf(subcategoriesFuture, imagesFuture).join(); // Ensure all data is fetched before proceeding

        try {
            model.addAttribute("subcategories", subcategoriesFuture.get());
            model.addAttribute("images", imagesFuture.get());
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            return "error";
        }

        if (bindingResult.hasErrors()) {
            return "products/product-form";
        }

        CompletableFuture<Boolean> productExistsFuture = CompletableFuture.supplyAsync(() -> productService.findAll().stream()
                .anyMatch(productRes -> product.getName().equals(productRes.getName()) &&
                        (product.getProductId() == null || !product.getProductId().equals(productRes.getProductId()))));

        boolean productExists = productExistsFuture.join();

        if (productExists) {
            model.addAttribute("errorMessage", "Product with that name already exists!");
            return "products/product-form";
        }

        Product productToAdd = (product.getProductId() != null && product.getProductId() > 0)
                ? productService.findById(product.getProductId())
                : new Product();

        productToAdd.setName(product.getName());
        productToAdd.setDescription(product.getDescription());
        productToAdd.setPrice(product.getPrice());
        productToAdd.setSubcategory(subcategoryService.findById(product.getSubcategoryId()));

        CompletableFuture<Void> saveProductFuture = CompletableFuture.runAsync(() -> productService.save(productToAdd));

        saveProductFuture.join();

        if (product.getSelectedImageId() != null) {
            CompletableFuture<Void> handleImagesFuture = CompletableFuture.runAsync(() -> {
                List<ProductImage> allProductImages = productImageService.findAll();
                for (var image : allProductImages) {
                    if (Objects.equals(image.getProduct().getProductId(), product.getProductId())) {
                        productImageService.deleteById(image.getProductImageId());
                    }
                }
                Image selectedImage = imageService.findById(product.getSelectedImageId());
                ProductImage productImage = new ProductImage(productToAdd, selectedImage);
                productImageService.save(productImage);
            });

            handleImagesFuture.join();
        }

        return "redirect:/webShop/admin/products/list";
    }

    @GetMapping("admin/products/delete")
    public String delete(@RequestParam("productId") int theId){
        CompletableFuture<List<ProductImage>> productImagesFuture = CompletableFuture.supplyAsync(() -> productImageService.findByProduct_ProductId(Integer.toUnsignedLong(theId)));

        CompletableFuture<Void> deleteProductImagesFuture = productImagesFuture.thenAccept(productImages -> {
            for (ProductImage productImage : productImages) {
                productImageService.deleteById(productImage.getProductImageId());
            }
        });
        deleteProductImagesFuture.join();

        CompletableFuture<Void> deleteProductFuture = CompletableFuture.runAsync(() -> productService.deleteById(theId));

        deleteProductFuture.join();

        return "redirect:/webShop/admin/products/list";
    }
}
