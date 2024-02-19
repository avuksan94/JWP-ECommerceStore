package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.OrderItem;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product findById(long id);
    Product save(Product obj);
    void deleteById(long id);
    List<Product> findByNameLike(String keyword);
    List<Product> findByKeyword(String keyword);
    Product getProductById(long productId);
}
