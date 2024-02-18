package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.ShoppingCartService;
import hr.algebra.dal.webshop2024dal.Entity.CartItem;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
import hr.algebra.dal.webshop2024dal.Repository.CartItemRepository;
import hr.algebra.dal.webshop2024dal.Repository.ProductRepository;
import hr.algebra.dal.webshop2024dal.Repository.ShoppingCartRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepo;
    private final ProductRepository productRepo;
    private final CartItemRepository cartItemRepo;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepo, ProductRepository productRepo, CartItemRepository cartItemRepo) {
        this.shoppingCartRepo = shoppingCartRepo;
        this.productRepo = productRepo;
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public List<ShoppingCart> findAll() {
        return shoppingCartRepo.findAll();
    }

    @Override
    public ShoppingCart findById(long id) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepo.findById(id);

        if (shoppingCartOptional.isEmpty()){
            throw new CustomNotFoundException("Shopping cart id not found - " + id);
        }
        return shoppingCartOptional.get();
    }

    @Override
    public Optional<ShoppingCart> findBySessionId(String sessionId) {
        return shoppingCartRepo.findBySessionId(sessionId);
    }

    @Override
    public Optional<ShoppingCart> findByUsername(String username) {
        return shoppingCartRepo.findByUsername(username);
    }

    @Override
    @Transactional
    public ShoppingCart save(ShoppingCart obj) {
        return shoppingCartRepo.save(obj);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Optional<ShoppingCart> checkIfExists = shoppingCartRepo.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Shopping cart with that ID was not found: " + id);
        }
        shoppingCartRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void addItemToCart(String identifier, Long productId, Integer quantity, boolean isRegisteredUser) {
        ShoppingCart cart = findOrCreateCart(identifier, isRegisteredUser);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        Optional<CartItem> existingCartItem = cartItemRepo.findByShoppingCartAndProduct(cart, product);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemRepo.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem(cart, product, quantity);
            cartItemRepo.save(newCartItem);
        }
    }

    @Override
    @Transactional
    public void removeItemFromCart(String identifier, Long productId, Integer quantity, boolean isRegisteredUser) {
        ShoppingCart cart = findOrCreateCart(identifier, isRegisteredUser);
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        Optional<CartItem> existingCartItem = cartItemRepo.findByShoppingCartAndProduct(cart, product);

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() - quantity;

            if (newQuantity <= 0) {
                cartItemRepo.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItemRepo.save(cartItem);
            }
        } else {
            throw new IllegalArgumentException("Cart item not found with Product ID: " + productId + " in cart");
        }
    }

    @Override
    @Transactional
    public ShoppingCart findOrCreateCart(String sessionIdOrUsername, boolean isRegisteredUser) {
        if (isRegisteredUser) {
            return shoppingCartRepo.findByUsername(sessionIdOrUsername)
                    .orElseGet(() -> shoppingCartRepo.save(new ShoppingCart(sessionIdOrUsername, null)));
        } else {
            return shoppingCartRepo.findBySessionId(sessionIdOrUsername)
                    .orElseGet(() -> shoppingCartRepo.save(new ShoppingCart(null, sessionIdOrUsername)));
        }
    }

    @Override
    @Transactional
    public void linkCartToUser(String sessionId, String username) {
        ShoppingCart cart = shoppingCartRepo.findBySessionId(sessionId)
                .orElse(new ShoppingCart());
        cart.setUsername(username);
        shoppingCartRepo.save(cart);
    }

    @Override
    public int getCartItemCount(String sessionIdOrUsername, boolean isRegistered) {
        ShoppingCart cart = findOrCreateCart(sessionIdOrUsername,isRegistered);
        return cartItemRepo.findByShoppingCart(cart)
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

}
