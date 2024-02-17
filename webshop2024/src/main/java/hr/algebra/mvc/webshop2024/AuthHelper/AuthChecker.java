package hr.algebra.mvc.webshop2024.AuthHelper;

import hr.algebra.bl.webshop2024bl.Service.UserService;
import hr.algebra.dal.webshop2024dal.Entity.Authority;

import java.security.Principal;
import java.util.Set;

public class AuthChecker {
    private final UserService userService;

    public AuthChecker(UserService userService) {
        this.userService = userService;
    }

    public Set<Authority> getRolesForUserByPrinciple(Principal principal){
        return userService.findByUsername(principal.getName()).getAuthorities();
    }
}
