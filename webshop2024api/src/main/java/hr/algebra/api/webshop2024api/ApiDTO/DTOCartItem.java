package hr.algebra.api.webshop2024api.ApiDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOCartItem {
    private Long cartItemId;
    private DTOShoppingCart shoppingCart;
    private DTOProduct product;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 100, message = "Quantity must be less than or equal to 100")
    private Integer quantity;

    public DTOCartItem(DTOShoppingCart shoppingCart, DTOProduct product, Integer quantity) {
        this.shoppingCart = shoppingCart;
        this.product = product;
        this.quantity = quantity;
    }
}
