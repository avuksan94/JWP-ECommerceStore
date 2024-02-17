package hr.algebra.mvc.webshop2024.ViewModel;

import hr.algebra.dal.webshop2024dal.Entity.ShoppingCart;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class CartItemVM {
    private Long cartItemId;
    private Long cartId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    //Need this for displaying images
    private String imageUrls;
    private Long selectedImageId;

    public CartItemVM(Long cartId, Long productId, String productName, BigDecimal productPrice, Integer quantity, String imageUrls, Long selectedImageId) {
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.imageUrls = imageUrls;
        this.selectedImageId = selectedImageId;
    }

    public CartItemVM(Long cartItemId, Long cartId, Long productId, String productName, BigDecimal productPrice, Integer quantity, String imageUrls, Long selectedImageId) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.imageUrls = imageUrls;
        this.selectedImageId = selectedImageId;
    }
}
