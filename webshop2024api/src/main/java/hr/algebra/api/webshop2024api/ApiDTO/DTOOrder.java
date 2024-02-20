package hr.algebra.api.webshop2024api.ApiDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOOrder {
    private Long orderId;
    @NotEmpty(message = "Username name is required")
    private String username;
    private LocalDateTime purchaseDate;
    private BigDecimal totalAmount;
    @NotEmpty(message = "Payment method is required")
    private String paymentMethod;

    public DTOOrder(String username, LocalDateTime purchaseDate, BigDecimal totalAmount, String paymentMethod) {
        this.username = username;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }
}
