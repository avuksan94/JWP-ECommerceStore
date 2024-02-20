package hr.algebra.mvc.webshop2024.ViewModel;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class ProductVM {
    private Long productId;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Description is required")
    private String description;
    @NotNull(message = "Price cannot be blank")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @DecimalMax(value = "10000.0", message = "Price must be less than 10000")
    @Digits(integer=6, fraction=2, message = "Price must not exceed 6 digits in the integer part and 2 digits in the fraction part")
    private BigDecimal price;
    @NotNull(message = "Subcategory ID must be selected")
    @Min(value = 1, message = "Please select a valid subcategory")
    private Long subcategoryId;
    private String imageUrls;
    private Long selectedImageId;

    public ProductVM(String name, String description, BigDecimal price, Long subcategoryId, String imageUrls) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.subcategoryId = subcategoryId;
        this.imageUrls = imageUrls;
    }

    public ProductVM(String name, String description, BigDecimal price, Long subcategoryId, String imageUrls, Long selectedImageId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.subcategoryId = subcategoryId;
        this.imageUrls = imageUrls;
        this.selectedImageId = selectedImageId;
    }
}
