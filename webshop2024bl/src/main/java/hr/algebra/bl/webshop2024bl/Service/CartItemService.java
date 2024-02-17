package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.CartItem;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    List<CartItem> findAll();
    CartItem findById(long id);
    Optional<CartItem> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product);
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
    CartItem save(CartItem obj);
    void deleteById(long id);
}
