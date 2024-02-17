package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.*;
import hr.algebra.dal.webshop2024dal.Entity.*;
import hr.algebra.mvc.webshop2024.ViewModel.CartItemVM;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
                                HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        shoppingCartService.addItemToCart(sessionId, productId, quantity, false);
        return "redirect:/webShop/products/list";
    }

    @PostMapping("/shopping/removeFromCartCart")
    public String removeItemFromCart(@RequestParam("productId") Long productId,
                                @RequestParam("quantity") Integer quantity,
                                HttpServletRequest request) {
        String sessionId = request.getSession().getId();
        shoppingCartService.removeItemFromCart(sessionId, productId, quantity, false);
        return "redirect:/webShop/products/list";
    }

    @PostMapping("/shopping/changeQuantity")
    public String changeQuantityInCart(@RequestParam("productId") Long productId,
                                       @RequestParam("quantity") Integer quantity,
                                       HttpServletRequest request) {
        String sessionId = request.getSession().getId();

        if (quantity > 0) {
            shoppingCartService.addItemToCart(sessionId, productId, quantity, false);
        } else if (quantity < 0) {
            shoppingCartService.removeItemFromCart(sessionId, productId, Math.abs(quantity), false);
        }
        return "redirect:/webShop/shopping/cart";
    }

    @GetMapping("/shopping/cartItemCount")
    @ResponseBody
    public int getCartItemCount(HttpServletRequest request) {
        String sessionIdOrUsername = request.getSession().getId();
        boolean isRegistered = false;
        return shoppingCartService.getCartItemCount(sessionIdOrUsername, isRegistered);
    }

    @GetMapping("/shopping/cart")
    public String viewShoppingCart(Model model, HttpServletRequest request) {
        String sessionId = request.getSession().getId();

        Optional<ShoppingCart> shoppingCart = shoppingCartService.findBySessionId(sessionId);

        if (shoppingCart.isEmpty()) {
            return "redirect:/webShop/products/list";
        }

        List<CartItem> realCartItems = cartItemService.findByShoppingCart(shoppingCart.get());
        List<CartItemVM> cartItems = new ArrayList<>();
        List<ProductImage> productImages = productImageService.findAll();

        for (var realCartItem : realCartItems) {
            CartItemVM cartItem = new CartItemVM();
            String imageLink = "https://nayemdevs.com/wp-content/uploads/2020/03/default-product-image.png"; // Default image link

            for (var image : productImages) {
                if (Objects.equals(image.getProduct().getProductId(), realCartItem.getProduct().getProductId())) {
                    imageLink = image.getImage().getImageUrl();
                    break;
                }
            }

            cartItem.setCartItemId(realCartItem.getCartItemId());
            cartItem.setCartId(realCartItem.getShoppingCart().getCartId());
            cartItem.setProductId(realCartItem.getProduct().getProductId());
            cartItem.setProductName(realCartItem.getProduct().getName());
            cartItem.setProductPrice(realCartItem.getProduct().getPrice());
            cartItem.setQuantity(realCartItem.getQuantity());
            cartItem.setImageUrls(imageLink);

            cartItems.add(cartItem);
        }

        model.addAttribute("cartItems", cartItems);

        return "shoppingcarts/shoppingcart-display";
    }


}
