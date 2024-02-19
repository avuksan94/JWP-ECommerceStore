package hr.algebra.dal.webshop2024dal.Repository;

import hr.algebra.dal.webshop2024dal.Entity.CartItem;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findByNameLike(String keyword);
    Product getProductByProductId(long productId);

    //findByName didnt work so i wrote my own query
    @Query(value = "SELECT * FROM Products p WHERE p.name LIKE CONCAT('%', :keyword, '%')", nativeQuery = true)
    List<Product> findByKeyword(@Param("keyword") String keyword);

}
