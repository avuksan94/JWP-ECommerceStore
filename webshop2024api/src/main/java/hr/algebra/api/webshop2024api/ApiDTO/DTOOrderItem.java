package hr.algebra.api.webshop2024api.ApiDTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOOrderItem {
    private Long orderItemId;
    private DTOOrder order;
    private DTOProduct product;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Max(value = 100, message = "Quantity must be less than or equal to 100")
    private Integer quantity;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Price must be less than 10000")
    @Digits(integer=6, fraction=2, message = "Price must not exceed 6 digits in the integer part and 2 digits in the fraction part")
    private BigDecimal price;

    public DTOOrderItem(DTOOrder order, DTOProduct product, Integer quantity, BigDecimal price) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
