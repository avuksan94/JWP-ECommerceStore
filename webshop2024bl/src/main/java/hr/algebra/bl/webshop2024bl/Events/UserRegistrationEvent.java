package hr.algebra.bl.webshop2024bl.Events;

import hr.algebra.dal.webshop2024dal.Entity.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

//https://medium.com/hprog99/mastering-events-in-spring-boot-a-comprehensive-guide-86348f968fc6

@Getter
public class UserRegistrationEvent extends ApplicationEvent {
    private final User user;

    public UserRegistrationEvent(Object source, User user) {
        super(source);
        this.user = user;
    }
}
