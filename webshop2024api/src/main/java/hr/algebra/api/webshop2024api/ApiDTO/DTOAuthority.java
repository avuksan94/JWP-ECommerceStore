package hr.algebra.api.webshop2024api.ApiDTO;

import hr.algebra.dal.webshop2024dal.Enum.Role;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOAuthority {
    private Long id;
    private DTOUser user;
    private Role authority;

    public DTOAuthority(DTOUser user, Role authority) {
        this.user = user;
        this.authority = authority;
    }
}
