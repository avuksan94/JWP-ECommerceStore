package hr.algebra.mvc.webshop2024.Advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ModelAttribute("navbarPath")
    public String navbarPath(Principal principal) {
        return (principal != null) ? "fragments/navbarShopper" : "fragments/navbarPlain";
    }
}
