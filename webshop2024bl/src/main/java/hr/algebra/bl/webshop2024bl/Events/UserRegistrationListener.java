package hr.algebra.bl.webshop2024bl.Events;

import hr.algebra.dal.webshop2024dal.Entity.Notification;
import hr.algebra.dal.webshop2024dal.Entity.User;
import hr.algebra.dal.webshop2024dal.Repository.NotificationRepository;
import hr.algebra.dal.webshop2024dal.Repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserRegistrationListener implements ApplicationListener<UserRegistrationEvent> {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public UserRegistrationListener(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        User getAdminUser = userRepository.findByUsername("admin");
        notificationRepository.save(
                new Notification(
                        getAdminUser,
                        "New account creation",
                        "New account has been created!",
                        LocalDateTime.now(),
                        null,
                        false
                )
        );
    }
}
