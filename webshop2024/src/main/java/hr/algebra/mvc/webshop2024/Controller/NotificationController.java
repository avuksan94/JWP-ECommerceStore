package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.NotificationService;
import hr.algebra.dal.webshop2024dal.Entity.*;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.DTO.DTONotification;
import hr.algebra.mvc.webshop2024.Mapper.NotificationMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("webShop")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationMapper notificationMapper;

    public NotificationController(NotificationService notificationService, NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping("admin/notifications/list")
    public String list(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<Notification>> notificationFuture = CompletableFuture.supplyAsync(() -> notificationService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(notificationFuture);

        CompletableFuture<List<DTONotification>> realNotificationsFuture = allFutures.thenApply(v -> {
            List<Notification> notifications = notificationFuture.join();
            List<DTONotification> realNotifications = new ArrayList<>();
            for (var notification : notifications) {
                realNotifications.add(notificationMapper.NotificationToDTONotification(notification));
            }
            return realNotifications;
        });

        List<DTONotification> realNotifications = realNotificationsFuture.get();

        theModel.addAttribute("notifications", realNotifications);

        return "notifications/list-notifications-admin";
    }

    @GetMapping("admin/notifications/showSelectedNotification")
    public String showSelectedNotification(@RequestParam("notificationId") int theId, Model theModel){
        CompletableFuture<Notification> notificationFuture = CompletableFuture.supplyAsync(() -> notificationService.findById(theId));

        CompletableFuture.allOf(notificationFuture).join();

        try {
            Notification notification = notificationFuture.get();
            notification.setReadAt(LocalDateTime.now());
            notification.setViewed(true);

            theModel.addAttribute("notification", notification);
        } catch (InterruptedException | ExecutionException e) {
            return "error";
        }

        return "notifications/notification-display";
    }
}
