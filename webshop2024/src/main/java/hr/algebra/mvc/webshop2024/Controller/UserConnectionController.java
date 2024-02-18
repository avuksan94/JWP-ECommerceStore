package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.UserConnectionService;
import hr.algebra.dal.webshop2024dal.Entity.UserConnection;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("webShop")
public class UserConnectionController {
    private final UserConnectionService userConnectionService;

    public UserConnectionController(UserConnectionService userConnectionService) {
        this.userConnectionService = userConnectionService;
    }
}
