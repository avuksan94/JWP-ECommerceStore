package hr.algebra.api.webshop2024api.ApiDTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOProduct {
    private Long productId;
    @NotEmpty(message = "Product name is required")
    private String name;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Price must be less than 10000")
    @Digits(integer=6, fraction=2, message = "Price must not exceed 6 digits in the integer part and 2 digits in the fraction part")
    private BigDecimal price;
    private DTOSubcategory subcategory;

    public DTOProduct(String name, String description, BigDecimal price, DTOSubcategory subcategory) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.subcategory = subcategory;
    }
}
