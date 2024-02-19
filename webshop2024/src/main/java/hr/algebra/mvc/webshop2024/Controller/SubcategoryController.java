package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.CategoryService;
import hr.algebra.bl.webshop2024bl.Service.SubcategoryService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.DTO.DTOSubcategory;
import hr.algebra.mvc.webshop2024.Mapper.CategoryMapper;
import hr.algebra.mvc.webshop2024.Mapper.SubcategoryMapper;
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
import java.util.stream.Collectors;

@Controller
@RequestMapping("webShop")
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;
    private final SubcategoryMapper subcategoryMapper;
    private final CategoryMapper categoryMapper;

    public SubcategoryController(SubcategoryService subcategoryService, CategoryService categoryService, SubcategoryMapper subcategoryMapper, CategoryMapper categoryMapper) {
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
        this.subcategoryMapper = subcategoryMapper;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("admin/subcategories/list")
    public String list(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Subcategory>> subcategoryFuture = CompletableFuture.supplyAsync(() -> subcategoryService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(subcategoryFuture);

        CompletableFuture<List<DTOSubcategory>> realSubCategoriesFuture = allFutures.thenApply(v -> {
            List<Subcategory> subcategories = subcategoryFuture.join();
            List<DTOSubcategory> realSubCategories = new ArrayList<>();
            for (var subcategory : subcategories) {
                DTOSubcategory subcat = new DTOSubcategory();
                subcat.setSubcategoryId(subcategory.getSubcategoryId());
                subcat.setName(subcategory.getName());

                DTOCategory category = new DTOCategory();
                category.setCategoryId(subcategory.getCategory().getCategoryId());
                category.setName(subcategory.getCategory().getName());

                subcat.setCategory(categoryMapper.CategoryItemToDTOCategory(
                        new Category(subcategory.getCategory().getCategoryId(),
                                     subcategory.getCategory().getName())));

                realSubCategories.add(subcat);
            }
            return realSubCategories;
        });

        List<DTOSubcategory> realSubCategories = realSubCategoriesFuture.get();

        theModel.addAttribute("subcategories", realSubCategories);

        return "subcategories/list-subcategories";
    }

    @GetMapping("admin/subcategories/showFormForAddSubcategory")
    public String showFormForAddSubcategory(Model theModel){
        //create the model attribute to bind form data
        DTOSubcategory subcategory =  new DTOSubcategory();
        theModel.addAttribute("subcategory", subcategory);

        List<DTOCategory> categories = new ArrayList<>();
        List<Category> allCategories = categoryService.findAll();

        allCategories.forEach(category ->
                categories.add(
                        new DTOCategory(category.getCategoryId(), category.getName())
                ));

        theModel.addAttribute("categories", categories);

        return "subcategories/subcategory-form";
    }

    @GetMapping("admin/subcategories/showFormForUpdateSubcategory")
    public String showFormForUpdateSubcategory(@RequestParam("subcategoryId") int theId, Model theModel){
        CompletableFuture<Subcategory> subcategoryFuture = CompletableFuture.supplyAsync(() -> subcategoryService.findById(theId));
        CompletableFuture<List<Category>> allCategoriesFuture = CompletableFuture.supplyAsync(() -> categoryService.findAll());

        CompletableFuture.allOf(subcategoryFuture, allCategoriesFuture).join();

        Subcategory subcategory = subcategoryFuture.join();

        DTOSubcategory dtoSubcategory = new DTOSubcategory(subcategory.getSubcategoryId(),
                        subcategory.getName(),
                        new DTOCategory(subcategory.getCategory().getCategoryId(),
                        subcategory.getCategory().getName()));

        theModel.addAttribute("subcategory", dtoSubcategory);

        List<DTOCategory> categories = allCategoriesFuture.join().stream()
                .map(cat -> new DTOCategory(cat.getCategoryId(), cat.getName()))
                .collect(Collectors.toList());

        theModel.addAttribute("categories", categories);

        return "subcategories/subcategory-form";
    }

    @PostMapping("admin/subcategories/save")
    public String saveSubcategory(@Valid @ModelAttribute("subcategory") DTOSubcategory subcategory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            CompletableFuture<List<DTOCategory>> categoriesFuture = CompletableFuture.supplyAsync(() -> categoryService.findAll().stream()
                    .map(category -> new DTOCategory(category.getCategoryId(), category.getName()))
                    .collect(Collectors.toList()));

            model.addAttribute("categories", categoriesFuture.join());
            return "subcategories/subcategory-form";
        }

        CompletableFuture<List<Subcategory>> allDBSubcategoriesFuture = CompletableFuture.supplyAsync(() -> subcategoryService.findAll());

        allDBSubcategoriesFuture.thenApply(allDBSubcategories -> allDBSubcategories.stream()
                .filter(subcategoryRes -> subcategory.getName().equals(subcategoryRes.getName()))
                .findFirst()).join().ifPresentOrElse(result -> {
            model.addAttribute("errorMessage", "Subcategory with that name already exists!");

            CompletableFuture<List<DTOCategory>> categoriesFuture = CompletableFuture.supplyAsync(() -> categoryService.findAll().stream()
                    .map(category -> new DTOCategory(category.getCategoryId(), category.getName()))
                    .collect(Collectors.toList()));

            model.addAttribute("categories", categoriesFuture.join());
        }, () -> {
            CompletableFuture.runAsync(() -> subcategoryService.save(subcategoryMapper.DTOSubcategoryToSubcategory(subcategory)));
        });

        if (model.containsAttribute("errorMessage")) {
            return "subcategories/subcategory-form";
        } else {
            return "redirect:/webShop/admin/subcategories/list";
        }
    }

    @GetMapping("admin/subcategories/delete")
    public String delete(@RequestParam("subcategoryId") int theId){
        CompletableFuture.runAsync(() -> subcategoryService.deleteById(theId)).join();
        return "redirect:/webShop/admin/subcategories/list";
    }
}
