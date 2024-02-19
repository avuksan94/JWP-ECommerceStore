package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> findAll();
    ProductImage findById(long id);
    List<ProductImage> findByProduct_ProductId(Long productId);
    ProductImage save(ProductImage obj);
    void deleteById(long id);
}
