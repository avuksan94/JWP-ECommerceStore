package hr.algebra.bl.webshop2024bl.Service;

import hr.algebra.dal.webshop2024dal.Entity.Authority;
import hr.algebra.dal.webshop2024dal.Entity.Notification;

import java.util.List;

public interface NotificationService {
    List<Notification> findAll();
    Notification findById(long id);
    Notification save(Notification obj);
    void deleteById(long id);
}
