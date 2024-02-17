package hr.algebra.dal.webshop2024dal.Entity;

import hr.algebra.dal.webshop2024dal.Enum.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authorities")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING) //annotation means that the enum will be persisted as a String !
    @Column(name = "authority", length = 50, nullable = false)
    private Role authority;

    public Authority(User user, Role authority) {
        this.user = user;
        this.authority = authority;
    }
}