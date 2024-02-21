package hr.algebra.mvc.webshop2024.DTO;

import hr.algebra.dal.webshop2024dal.Entity.User;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
@Setter
@ToString
public class DTONotification {
    private Long notificationId;
    private User user;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;
    private boolean viewed;

    public DTONotification(User user, String title, String message, LocalDateTime createdAt, LocalDateTime readAt, boolean viewed) {
        this.user = user;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.viewed = viewed;
    }

    public DTONotification(Long notificationId, User user, String title, String message, LocalDateTime createdAt, LocalDateTime readAt, boolean viewed) {
        this.notificationId = notificationId;
        this.user = user;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.readAt = readAt;
        this.viewed = viewed;
    }
}
