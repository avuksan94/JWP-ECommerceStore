package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.CategoryService;
import hr.algebra.bl.webshop2024bl.Service.SubcategoryService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.DTO.DTOSubcategory;
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
public class SubcategoryController {
    private final SubcategoryService subcategoryService;
    private final CategoryService categoryService;

    public SubcategoryController(SubcategoryService subcategoryService, CategoryService categoryService) {
        this.subcategoryService = subcategoryService;
        this.categoryService = categoryService;
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

                subcat.setCategory(category);

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
        Subcategory subcategory = subcategoryService.findById(theId);

        DTOSubcategory dtoSubcategory = new DTOSubcategory();
        dtoSubcategory.setSubcategoryId(subcategory.getSubcategoryId());
        dtoSubcategory.setName(subcategory.getName());

        DTOCategory category = new DTOCategory();
        category.setCategoryId(subcategory.getCategory().getCategoryId());
        category.setName(subcategory.getCategory().getName());

        dtoSubcategory.setCategory(category);

        theModel.addAttribute("subcategory", dtoSubcategory);

        List<DTOCategory> categories = new ArrayList<>();
        List<Category> allCategories = categoryService.findAll();

        allCategories.forEach(cat ->
                categories.add(
                        new DTOCategory(cat.getCategoryId(), cat.getName())
                ));

        theModel.addAttribute("categories", categories);

        //send over to our form
        return "subcategories/subcategory-form";
    }

    @PostMapping("admin/subcategories/save")
    public String saveSubcategory(@Valid @ModelAttribute("subcategory") DTOSubcategory subcategory, BindingResult bindingResult, Model model) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "subcategories/subcategory-form";
        }

        List<Subcategory> allDBSubcategories = subcategoryService.findAll();

        Optional<Subcategory> result = allDBSubcategories.stream()
                .filter(subcategoryRes -> subcategory.getName().equals(subcategoryRes.getName()))
                .findFirst();

        if(result.isPresent()){
            model.addAttribute("errorMessage", "Subcategory with that name already exists!");
            return "subcategories/subcategory-form";
        }

        Subcategory subcategoryToSave = new Subcategory();
        subcategoryToSave.setSubcategoryId(subcategory.getSubcategoryId());
        subcategoryToSave.setName(subcategory.getName());
        subcategoryToSave.setCategory(new Category(subcategory.getCategory().getCategoryId(), subcategory.getCategory().getName()));
        subcategoryService.save(subcategoryToSave);

        return "redirect:/webShop/admin/subcategories/list";
    }

    @GetMapping("admin/subcategories/delete")
    public String delete(@RequestParam("subcategoryId") int theId){
        subcategoryService.deleteById(theId);

        return "redirect:/webShop/admin/subcategories/list";
    }
}
