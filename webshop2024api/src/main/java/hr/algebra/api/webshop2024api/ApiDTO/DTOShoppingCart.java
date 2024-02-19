package hr.algebra.api.webshop2024api.ApiDTO;

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
