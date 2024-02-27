package hr.algebra.mvc.webshop2024.DTO;

import hr.algebra.dal.webshop2024dal.Entity.Authority;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOUser {
    @NotEmpty(message = "Username is required!")
    private String username;
    @NotEmpty(message = "Password is required!")
    private String password;
    private boolean enabled;
    @NotEmpty(message = "Email is required!")
    @Email(message = "Email must be a valid email address!")
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
