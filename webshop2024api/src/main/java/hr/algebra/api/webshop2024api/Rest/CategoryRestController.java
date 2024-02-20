package hr.algebra.api.webshop2024api.Rest;


import hr.algebra.api.webshop2024api.ApiDTO.DTOCategory;
import hr.algebra.api.webshop2024api.ApiMapper.CategoryMapper;
import hr.algebra.bl.webshop2024bl.Service.CategoryService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webShopApi")
public class CategoryRestController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryRestController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categories/allCategories")
    public CompletableFuture<List<DTOCategory>> findAllCategories(){
        return CompletableFuture.completedFuture(
                categoryService.findAll()
                        .stream()
                        .map(categoryMapper::CategoryItemToDTOCategory)
                        .collect(Collectors.toList())
        );
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/categories/{categoryId}")
    public CompletableFuture<DTOCategory> findCategoryById(@PathVariable int categoryId) {
        Category category = categoryService.findById(categoryId);
        if (category == null) {
            throw new CustomNotFoundException("Category not found for id: " + categoryId);
        }
        return CompletableFuture.completedFuture(categoryMapper.CategoryItemToDTOCategory(category));
    }

    @Async
    @ResponseBody
    @PostMapping("/categories")
    public CompletableFuture<DTOCategory> createCategory(@Valid @RequestBody DTOCategory dtoCategory) {
        Category newCategory = categoryMapper.DTOCategoryToCategory(dtoCategory);
        Category savedCategory = categoryService.save(newCategory);
        return CompletableFuture.supplyAsync(() -> categoryMapper.CategoryItemToDTOCategory(savedCategory))
                .thenApplyAsync(savedDtoCategory -> {
                    return savedDtoCategory;
                });
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/categories/{categoryId}")
    public CompletableFuture<DTOCategory> updateCategory(@PathVariable int categoryId, @Valid @RequestBody DTOCategory category){
        Category extistingCategory = categoryService.findById(categoryId);

        if (extistingCategory == null) {
            throw new CustomNotFoundException("Category not found for id: " + categoryId);
        }

        extistingCategory.setName(category.getName());

        Category updatedCategory = categoryService.save(extistingCategory);

        return CompletableFuture.completedFuture(categoryMapper.CategoryItemToDTOCategory(updatedCategory));
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/categories/{categoryId}")
    public CompletableFuture<String> deleteCategory(@PathVariable int categoryId){
        Category categoryToDelete = categoryService.findById(categoryId);
        if (categoryToDelete == null){
            throw new CustomNotFoundException(("Category id not found - " + categoryId));
        }
        categoryService.deleteById(categoryId);

        return CompletableFuture.completedFuture("Deleted category with ID: " + categoryId);
    }
}
