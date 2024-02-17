package hr.algebra.mvc.webshop2024.DTO;

import hr.algebra.dal.webshop2024dal.Entity.Authority;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTOUser {
    private String username;
    private String password;
    private boolean enabled;
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