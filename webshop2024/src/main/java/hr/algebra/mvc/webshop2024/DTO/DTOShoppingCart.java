package hr.algebra.mvc.webshop2024.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOShoppingCart {
    private Long cartId;
    private String username;
    private String sessionId;

    public DTOShoppingCart(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
    }
}
