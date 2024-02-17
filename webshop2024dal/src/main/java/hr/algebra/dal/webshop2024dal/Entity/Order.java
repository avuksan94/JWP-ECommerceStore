package hr.algebra.dal.webshop2024dal.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Column(name = "username")
    private String username;

    @Column(name = "purchase_date", nullable = false)
    private Date purchaseDate;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    public Order(String username, Date purchaseDate, BigDecimal totalAmount, String paymentMethod) {
        this.username = username;
        this.purchaseDate = purchaseDate;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }
}
