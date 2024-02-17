package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.CategoryService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ProductImage;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.ViewModel.ProductVM;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("webShop")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("admin/categories/list")
    public String list(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Category>> categoryFuture = CompletableFuture.supplyAsync(() -> categoryService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(categoryFuture);

        CompletableFuture<List<DTOCategory>> realCategoriesFuture = allFutures.thenApply(v -> {
            List<Category> categories = categoryFuture.join();
            List<DTOCategory> realCategories = new ArrayList<>();
            for (var category : categories) {
                DTOCategory cat = new DTOCategory();
                cat.setCategoryId(category.getCategoryId());
                cat.setName(category.getName());

                realCategories.add(cat);
            }
            return realCategories;
        });

        List<DTOCategory> realCategories = realCategoriesFuture.get();

        theModel.addAttribute("categories", realCategories);

        return "categories/list-categories";
    }

    @GetMapping("admin/categories/showFormForAddCategory")
    public String showFormForAddCategory(Model theModel){
        //create the model attribute to bind form data
        DTOCategory category =  new DTOCategory();
        theModel.addAttribute("category", category);

        return "categories/category-form";
    }

    @GetMapping("admin/categories/showFormForUpdateCategory")
    public String showFormForUpdateCategory(@RequestParam("categoryId") int theId, Model theModel){
        Category category = categoryService.findById(theId);

        DTOCategory dtoCategory = new DTOCategory();
        dtoCategory.setCategoryId(category.getCategoryId());
        dtoCategory.setName(category.getName());

        theModel.addAttribute("category", dtoCategory);

        return "categories/category-form";
    }

    @PostMapping("admin/categories/save")
    public String saveCategory(@Valid @ModelAttribute("category") DTOCategory category, BindingResult bindingResult, Model model) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "categories/category-form";
        }

        List<Category> allDBCategories = categoryService.findAll();

        Optional<Category> result = allDBCategories.stream()
                .filter(categoryRes -> category.getName().equals(categoryRes.getName()))
                .findFirst();

        if(result.isPresent()){
            model.addAttribute("errorMessage", "Category with that name already exists!");
            return "categories/category-form";
        }

        Category categoryToSave = new Category();
        categoryToSave.setCategoryId(category.getCategoryId());
        categoryToSave.setName(category.getName());
        categoryService.save(categoryToSave);

        return "redirect:/webShop/admin/categories/list";
    }

    @GetMapping("admin/categories/delete")
    public String delete(@RequestParam("categoryId") int theId){
        categoryService.deleteById(theId);

        return "redirect:/webShop/admin/categories/list";
    }
}
