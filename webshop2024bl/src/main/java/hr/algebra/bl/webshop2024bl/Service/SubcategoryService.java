package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
import hr.algebra.dal.webshop2024dal.Entity.Subcategory;

import java.util.List;

public interface SubcategoryService {
    List<Subcategory> findAll();
    Subcategory findById(long id);
    Subcategory save(Subcategory obj);
    void deleteById(long id);
}
