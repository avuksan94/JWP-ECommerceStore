package hr.algebra.dal.webshop2024dal.Repository;

import hr.algebra.dal.webshop2024dal.Entity.CartItem;
import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    Optional<ShoppingCart> findBySessionId(String sessionId);
    Optional<ShoppingCart> findByUsername(String username);
}
