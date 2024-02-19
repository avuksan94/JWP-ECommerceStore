package hr.algebra.api.webshop2024api.Rest;

import hr.algebra.api.webshop2024api.ApiDTO.DTOCategory;
import hr.algebra.api.webshop2024api.ApiDTO.DTOSubcategory;
import hr.algebra.api.webshop2024api.ApiMapper.CategoryMapper;
import hr.algebra.api.webshop2024api.ApiMapper.SubcategoryMapper;
import hr.algebra.bl.webshop2024bl.Service.SubcategoryService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
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
public class SubcategoryRestController {
    private final SubcategoryService subcategoryService;
    private final SubcategoryMapper subcategoryMapper;
    private final CategoryMapper categoryMapper;

    public SubcategoryRestController(SubcategoryService subcategoryService, SubcategoryMapper subcategoryMapper, CategoryMapper categoryMapper) {
        this.subcategoryService = subcategoryService;
        this.subcategoryMapper = subcategoryMapper;
        this.categoryMapper = categoryMapper;
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subcategories/allSubcategories")
    public CompletableFuture<List<DTOSubcategory>> findAllSubcategories(){
        return CompletableFuture.completedFuture(
                subcategoryService.findAll()
                        .stream()
                        .map(subcategoryMapper::SubcategoryToDTOSubcategory)
                        .collect(Collectors.toList())
        );
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/subcategories/{subcategoryId}")
    public CompletableFuture<DTOSubcategory> findSubcategoryById(@PathVariable int subcategoryId) {
        Subcategory subcategory = subcategoryService.findById(subcategoryId);
        if (subcategory == null) {
            throw new CustomNotFoundException("Subcategory not found for id: " + subcategoryId);
        }
        return CompletableFuture.completedFuture(subcategoryMapper.SubcategoryToDTOSubcategory(subcategory));
    }

    @Async
    @ResponseBody
    @PostMapping("/subcategories")
    public CompletableFuture<DTOSubcategory> createSubcategory(@Valid @RequestBody DTOSubcategory dtoSubcategory) {
        Subcategory newSubcategory = subcategoryMapper.DTOSubcategoryToSubcategory(dtoSubcategory);
        Subcategory savedSubcategory = subcategoryService.save(newSubcategory);
        return CompletableFuture.supplyAsync(() -> subcategoryMapper.SubcategoryToDTOSubcategory(savedSubcategory))
                .thenApplyAsync(savedDtoCategory -> savedDtoCategory);
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/subcategories/{subcategoryId}")
    public CompletableFuture<DTOSubcategory> updateSubcategory(@PathVariable int subcategoryId, @Valid @RequestBody DTOSubcategory subcategory){
        Subcategory extistingSubcategory = subcategoryService.findById(subcategoryId);

        if (extistingSubcategory == null) {
            throw new CustomNotFoundException("Subcategory not found for id: " + subcategoryId);
        }

        extistingSubcategory.setName(subcategory.getName());
        extistingSubcategory.setCategory(categoryMapper.DTOCategoryToCategory(subcategory.getCategory()));

        Subcategory updatedSubategory = subcategoryService.save(extistingSubcategory);

        return CompletableFuture.completedFuture(subcategoryMapper.SubcategoryToDTOSubcategory(updatedSubategory));
    }

    @Async
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/subcategories/{subcategoryId}")
    public CompletableFuture<String> deleteSubcategory(@PathVariable int subcategoryId){
        Subcategory subcategoryToDelete = subcategoryService.findById(subcategoryId);
        if (subcategoryToDelete == null){
            throw new CustomNotFoundException(("Subcategory id not found - " + subcategoryId));
        }
        subcategoryService.deleteById(subcategoryId);

        return CompletableFuture.completedFuture("Deleted subcategory with ID: " + subcategoryId);
    }
}
