package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.CartItemService;
import hr.algebra.dal.webshop2024dal.Entity.Authority;
import hr.algebra.dal.webshop2024dal.Entity.CartItem;
import hr.algebra.dal.webshop2024dal.Entity.Product;
import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
import hr.algebra.dal.webshop2024dal.Repository.CartItemRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepo;

    public CartItemServiceImpl(CartItemRepository cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemRepo.findAll();
    }

    @Override
    public CartItem findById(long id) {
        Optional<CartItem> cartItemOptional = cartItemRepo.findById(id);

        if (cartItemOptional.isEmpty()){
            throw new CustomNotFoundException("Cart Item id not found - " + id);
        }

        return cartItemOptional.get();
    }

    @Override
    public Optional<CartItem> findByShoppingCartAndProduct(ShoppingCart shoppingCart, Product product) {
        return cartItemRepo.findByShoppingCartAndProduct(shoppingCart,product);
    }

    @Override
    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepo.findByShoppingCart(shoppingCart);
    }

    @Override
    public CartItem save(CartItem obj) {
        return cartItemRepo.save(obj);
    }

    @Override
    public void deleteById(long id) {
        Optional<CartItem> checkIfExists = cartItemRepo.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Cart Item with that ID was not found: " + id);
        }
        cartItemRepo.deleteById(id);
    }
}
