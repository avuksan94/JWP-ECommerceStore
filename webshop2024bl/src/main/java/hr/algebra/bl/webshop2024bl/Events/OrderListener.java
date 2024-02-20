package hr.algebra.bl.webshop2024bl.Events;

import hr.algebra.bl.webshop2024bl.Service.NotificationService;
import hr.algebra.dal.webshop2024dal.Entity.Notification;
import hr.algebra.dal.webshop2024dal.Entity.User;
import hr.algebra.dal.webshop2024dal.Repository.NotificationRepository;
import hr.algebra.dal.webshop2024dal.Repository.OrderRepository;
import hr.algebra.dal.webshop2024dal.Repository.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderListener implements ApplicationListener<OrderEvent> {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public OrderListener(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(OrderEvent event) {
        User getAdminUser = userRepository.findByUsername("admin");
        notificationRepository.save(
                new Notification(
                        getAdminUser,
                        "New order made",
                        "New order was made!",
                        LocalDateTime.now(),
                        null,
                        false
                )
        );
    }
}
