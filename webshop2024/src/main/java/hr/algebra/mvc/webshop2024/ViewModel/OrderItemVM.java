package hr.algebra.mvc.webshop2024.ViewModel;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class OrderItemVM {
    private Long orderItemId;
    private Long orderId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer quantity;
    private BigDecimal productSum;
    private String imageUrls;
    private Long selectedImageId;

    public OrderItemVM(Long orderId, Long productId, String productName, BigDecimal productPrice, Integer quantity, BigDecimal productSum, String imageUrls, Long selectedImageId) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productSum = productSum;
        this.imageUrls = imageUrls;
        this.selectedImageId = selectedImageId;
    }

    public OrderItemVM(Long orderItemId, Long orderId, Long productId, String productName, BigDecimal productPrice, Integer quantity, BigDecimal productSum, String imageUrls, Long selectedImageId) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.productSum = productSum;
        this.imageUrls = imageUrls;
        this.selectedImageId = selectedImageId;
    }
}
