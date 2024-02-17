package hr.algebra.dal.webshop2024dal.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class User {
    @Id
    @Column(name = "username", length = 255, unique = true, nullable = false)
    @NotEmpty(message = "Username is required")
    private String username;

    @Column(name = "password", length = 128, nullable = false)
    @NotEmpty(message = "Password is required")
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @NotEmpty(message = "Email is required")
    @Column(name = "email", length = 255, nullable = false)
    private String email;

    //https://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence-api
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Authority> authorities = new HashSet<>();

    public User(String password, boolean enabled, String email, Set<Authority> authorities) {
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.authorities = authorities;
    }
}