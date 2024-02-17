package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartService {
    List<ShoppingCart> findAll();
    ShoppingCart findById(long id);
    Optional<ShoppingCart> findBySessionId(String sessionId);
    ShoppingCart save(ShoppingCart obj);
    void deleteById(long id);
    void addItemToCart(String identifier, Long productId, Integer quantity, boolean isRegisteredUser);
    void removeItemFromCart(String identifier, Long productId, Integer quantity, boolean isRegisteredUser);
    ShoppingCart findOrCreateCart(String sessionIdOrUsername, boolean isRegisteredUser);
    void linkCartToUser(String sessionId, String username);
    int getCartItemCount(String sessionIdOrUsername, boolean isRegistered);
}
