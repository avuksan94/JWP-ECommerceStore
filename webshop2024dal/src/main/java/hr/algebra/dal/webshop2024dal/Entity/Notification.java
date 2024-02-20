package hr.algebra.dal.webshop2024dal.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Column(name = "title")
    private String title;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "viewed")
    private boolean viewed;

    public Notification(User user, String title, String message, LocalDateTime createdAt, LocalDateTime readAt, boolean viewed) {
        this.user = user;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.viewed = viewed;
    }

    public Notification(Long notificationId, User user, String title, String message, LocalDateTime createdAt, LocalDateTime readAt, boolean viewed) {
        this.notificationId = notificationId;
        this.user = user;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.viewed = viewed;
    }
}
