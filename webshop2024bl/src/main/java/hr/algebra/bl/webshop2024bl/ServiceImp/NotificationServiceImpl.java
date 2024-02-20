package hr.algebra.bl.webshop2024bl.ServiceImp;

import hr.algebra.bl.webshop2024bl.Service.NotificationService;
import hr.algebra.dal.webshop2024dal.Entity.Authority;
import hr.algebra.dal.webshop2024dal.Entity.Notification;
import hr.algebra.dal.webshop2024dal.Repository.NotificationRepository;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(long id) {
        Optional<Notification> notificationOptional = notificationRepository.findById(id);

        if (notificationOptional.isEmpty()){
            throw new CustomNotFoundException("Notification id not found - " + id);
        }

        return notificationOptional.get();
    }

    @Override
    public Notification save(Notification obj) {
        return notificationRepository.save(obj);
    }

    @Override
    public void deleteById(long id) {
        Optional<Notification> checkIfExists = notificationRepository.findById(id);
        if (checkIfExists.isEmpty()){
            throw new CustomNotFoundException("Notification with that ID was not found: " + id);
        }
        notificationRepository.deleteById(id);
    }
}
