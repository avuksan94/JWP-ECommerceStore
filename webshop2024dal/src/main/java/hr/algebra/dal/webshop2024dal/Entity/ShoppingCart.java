package hr.algebra.dal.webshop2024dal.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_cart")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @Column(name = "username")
    private String username;

    @Column(name = "session_id")
    private String sessionId;

    public ShoppingCart(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
    }
}
