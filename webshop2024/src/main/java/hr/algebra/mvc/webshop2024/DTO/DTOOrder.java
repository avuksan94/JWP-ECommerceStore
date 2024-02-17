package hr.algebra.mvc.webshop2024.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOOrder {
    private Long orderId;
    private String username;
    private Date purchaseDate;
    private BigDecimal totalAmount;
    private String paymentMethod;

    public DTOOrder(String username, Date purchaseDate, BigDecimal totalAmount, String paymentMethod) {
        this.username = username;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }
}
