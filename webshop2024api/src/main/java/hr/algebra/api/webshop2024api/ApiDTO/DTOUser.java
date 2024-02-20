package hr.algebra.api.webshop2024api.ApiDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOUser {
    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Password is required")
    private String password;
    private boolean enabled;
    @NotEmpty(message = "Email is required")
    private String email;
    private Set<DTOAuthority> authorities = new HashSet<>();

    public DTOUser(String username, String password, boolean enabled, String email, Set<DTOAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.authorities = authorities;
    }
}
