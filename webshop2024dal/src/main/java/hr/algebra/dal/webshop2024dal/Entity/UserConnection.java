package hr.algebra.dal.webshop2024dal.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_connections")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class UserConnection {
    @Id
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "last_connection", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime lastConnection;

    @Column(name = "ip_address", nullable = false, length = 45)
    private String ipAddress;

    public UserConnection(LocalDateTime lastConnection, String ipAddress) {
        this.lastConnection = lastConnection;
        this.ipAddress = ipAddress;
    }

    public UserConnection(String username, LocalDateTime lastConnection, String ipAddress) {
        this.username = username;
        this.lastConnection = lastConnection;
        this.ipAddress = ipAddress;
    }
}
