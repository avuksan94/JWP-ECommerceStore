package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.*;
import hr.algebra.dal.webshop2024dal.Consts.WebShopConsts;
import hr.algebra.dal.webshop2024dal.Entity.*;
import hr.algebra.mvc.webshop2024.ViewModel.CartItemVM;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("webShop")
public class ShoppingController {
    private final ShoppingCartService shoppingCartService;
    private final CartItemService cartItemService;
    private final ProductImageService productImageService;

    public ShoppingController(ShoppingCartService shoppingCartService, CartItemService cartItemService, ProductImageService productImageService) {
        this.shoppingCartService = shoppingCartService;
        this.cartItemService = cartItemService;
        this.productImageService = productImageService;
    }


    @PostMapping("/shopping/addToCart")
    public String addItemToCart(@RequestParam("productId") Long productId,
                                @RequestParam("quantity") Integer quantity,
                                HttpServletRequest request, Principal principal) {
        String identifier = principal != null ? principal.getName() : request.getSession().getId();
        boolean isUserRegistered = principal != null;

        CompletableFuture<Void> addItemFuture = CompletableFuture.runAsync(() ->
                shoppingCartService.addItemToCart(identifier, productId, quantity, isUserRegistered));
        addItemFuture.join();

        return "redirect:/webShop/products/list";
    }

    @PostMapping("/shopping/removeFromCartCart")
    public String removeItemFromCart(@RequestParam("productId") Long productId,
                                @RequestParam("quantity") Integer quantity,
                                HttpServletRequest request,Principal principal) {
        String identifier = principal != null ? principal.getName() : request.getSession().getId();
        boolean isUserRegistered = principal != null;

        CompletableFuture<Void> removeItemFuture = CompletableFuture.runAsync(() ->
                shoppingCartService.removeItemFromCart(identifier, productId, quantity, isUserRegistered));
        removeItemFuture.join();

        return "redirect:/webShop/products/list";
    }

    @PostMapping("/shopping/changeQuantity")
    public String changeQuantityInCart(@RequestParam("productId") Long productId,
                                       @RequestParam("quantity") Integer quantity,
                                       HttpServletRequest request,Principal principal) {
        String identifier = principal != null ? principal.getName() : request.getSession().getId();
        boolean isUserRegistered = principal != null;

        CompletableFuture<Void> changeQuantityFuture = CompletableFuture.runAsync(() -> {
            if (quantity > 0) {
                shoppingCartService.addItemToCart(identifier, productId, quantity, isUserRegistered);
            } else if (quantity < 0) {
                shoppingCartService.removeItemFromCart(identifier, productId, Math.abs(quantity), isUserRegistered);
            }
        });
        changeQuantityFuture.join();

        return "redirect:/webShop/shopping/cart";
    }

    @GetMapping("/shopping/cartItemCount")
    @ResponseBody
    public int getCartItemCount(HttpServletRequest request,Principal principal) {
        boolean isRegistered = false;
        if (principal != null) {
            isRegistered = true;
            return shoppingCartService.getCartItemCount(principal.getName(), isRegistered);
        }
        return shoppingCartService.getCartItemCount(request.getSession().getId(), isRegistered);
    }

    @GetMapping("/shopping/cart")
    public String viewShoppingCart(Model model, HttpServletRequest request,Principal principal) {
        String identifier = principal != null ? principal.getName() : request.getSession().getId();
        boolean isUserRegistered = principal != null;

        CompletableFuture<Optional<ShoppingCart>> shoppingCartFuture = isUserRegistered
                ? CompletableFuture.supplyAsync(() -> shoppingCartService.findByUsername(identifier))
                : CompletableFuture.supplyAsync(() -> shoppingCartService.findBySessionId(identifier));

        CompletableFuture<List<ProductImage>> productImagesFuture = CompletableFuture.supplyAsync(() ->
                productImageService.findAll());

        shoppingCartFuture.thenAcceptAsync(shoppingCart -> {
            if (shoppingCart.isPresent()) {
                List<CartItem> realCartItems = cartItemService.findByShoppingCart(shoppingCart.get());
                List<CartItemVM> cartItems = new ArrayList<>();

                // Synchronously wait for product images to ensure they are available for processing cart items.
                List<ProductImage> productImages = productImagesFuture.join();

                for (CartItem realCartItem : realCartItems) {
                    String imageLink = productImages.stream()
                            .filter(image -> Objects.equals(image.getProduct().getProductId(), realCartItem.getProduct().getProductId()))
                            .findFirst()
                            .map(image -> image.getImage().getImageUrl())
                            .orElse(WebShopConsts.DEFAULT_IMAGE_FILENAME);

                    CartItemVM cartItemVM = new CartItemVM(
                            realCartItem.getCartItemId(),
                            realCartItem.getShoppingCart().getCartId(),
                            realCartItem.getProduct().getProductId(),
                            realCartItem.getProduct().getName(),
                            realCartItem.getProduct().getPrice(),
                            realCartItem.getQuantity(),
                            imageLink);

                    cartItems.add(cartItemVM);
                }

                model.addAttribute("cartItems", cartItems);
            }
        }).join();

        if (shoppingCartFuture.isCompletedExceptionally()) {
            return "error";
        }

        return shoppingCartFuture.isDone() && shoppingCartFuture.join().isPresent()
                ? "shoppingcarts/shoppingcart-display"
                : "redirect:/webShop/products/list";
    }
}
