package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.UserConnectionService;
import hr.algebra.dal.webshop2024dal.Entity.Category;
import hr.algebra.dal.webshop2024dal.Entity.Order;
import hr.algebra.dal.webshop2024dal.Entity.UserConnection;
import hr.algebra.mvc.webshop2024.DTO.DTOCategory;
import hr.algebra.mvc.webshop2024.DTO.DTOUserConnection;
import hr.algebra.mvc.webshop2024.Mapper.UserConnectionMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("webShop")
public class UserConnectionController {
    private final UserConnectionService userConnectionService;
    private final UserConnectionMapper userConnectionMapper;

    public UserConnectionController(UserConnectionService userConnectionService, UserConnectionMapper userConnectionMapper) {
        this.userConnectionService = userConnectionService;
        this.userConnectionMapper = userConnectionMapper;
    }

    @GetMapping("admin/userConnections/list")
    public String list(Model theModel) throws ExecutionException, InterruptedException {
        CompletableFuture<List<UserConnection>> userConnectionsFuture = CompletableFuture.supplyAsync(() -> userConnectionService.findAll());

        CompletableFuture<Void> allFutures = CompletableFuture.allOf(userConnectionsFuture);

        CompletableFuture<List<DTOUserConnection>> realUserConnectionsFuture = allFutures.thenApply(v -> {
            List<UserConnection> userConnections = userConnectionsFuture.join();
            List<DTOUserConnection> realUserConnections = new ArrayList<>();
            for (var userConnection : userConnections) {
                userConnectionMapper.UserConnectionToDTOUserConnection(userConnection);
                realUserConnections.add(userConnectionMapper.UserConnectionToDTOUserConnection(userConnection));
            }
            return realUserConnections;
        });

        List<DTOUserConnection> realUserConnections = realUserConnectionsFuture.get();

        theModel.addAttribute("userConnections", realUserConnections);

        return "userconnections/list-connections";
    }
}
