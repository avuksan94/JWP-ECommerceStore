package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.ProductService;
import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Repository.ProductRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(long id) {
        Optional<Product> productOptional = productRepo.findById(id);

        if (productOptional.isEmpty()){
            throw new CustomNotFoundException("Product id not found - " + id);
        }
        return productOptional.get();
    }

    @Override
    public Product save(Product obj) {
        return productRepo.save(obj);
    }

    @Override
    public void deleteById(long id) {
        Optional<Product> checkIfExists = productRepo.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Product with that ID was not found: " + id);
        }
        productRepo.deleteById(id);
    }

    @Override
    public List<Product> findByNameLike(String keyword) {
        return productRepo.findByNameLike(keyword);
    }

    @Override
    public Product getProductById(long productId) {
        return productRepo.getProductByProductId(productId);
    }
}
