package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findById(long id);
    Category save(Category obj);
    void deleteById(long id);
}
