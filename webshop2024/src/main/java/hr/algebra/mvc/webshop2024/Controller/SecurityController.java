package hr.algebra.mvc.webshop2024.Controller;

import hr.algebra.bl.webshop2024bl.Service.UserService;
import hr.algebra.utils.CustomExceptions.CustomNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import hr.algebra.dal.webshop2024dal.Entity.*;

import java.util.List;

@Controller
@RequestMapping("webShop")
public class SecurityController {
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    public SecurityController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/security/loginUser")
    public String showMyLoginPage() {
        List<User> users = userService.findAll();

        String usersCount = Integer.toString(users.size());
        /*
        logger.info("Created users: " + usersCount);
        for (User user:
                users) {
            logger.info("Password" + user.getPassword());
            logger.info("Username" + user.getUsername());

            for (Authority r:
                    user.getAuthorities()) {
                logger.info(r.getAuthority().toString());
            }
        }
         */

        return "security/security-login";
    }

    @PostMapping("/security/manualLogout")
    public String manualLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/security/security-login?logoutManual=true";
    }

    @GetMapping("/security/showFormCreateUser")
    public String showFormCreateUser(Model theModel){
        User user = new User();
        theModel.addAttribute("user", user);

        return "security/security-registration";
    }

    @PostMapping("/security/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "security/security-registration";
        }

        try {
            // Try to find a user with the provided username
            User existingUserByUsername = userService.findByUsername(user.getUsername().trim());

            // If we are here, it means a user with that username already exists
            model.addAttribute("errorMessage", "User with that username already exists!");
            return "security/security-registration";
        } catch (CustomNotFoundException e) {

        }

        userService.createShopperUser(user.getUsername(),user.getPassword(),user.getEmail());
        //userService.createAdminUser(user.getUsername(),user.getPassword(),user.getEmail());

        return "redirect:/webShop/security/loginUser";
    }
}
