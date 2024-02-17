package hr.algebra.mvc.webshop2024.ViewModel;

import jakarta.validation.constraints.NotEmpty;
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
    private BigDecimal price;
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
