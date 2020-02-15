package ua.org.workshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kissik
 */
@Controller
public class PortalController {

    @GetMapping("/")
    public String welcome() {
        return Pages.WELCOME_PAGE;
    }

    @RequestMapping(value = "/access-denied")
    public String getAccessDenied() {
        return Pages.ACCESS_DENIED_PAGE;
    }

    @RequestMapping(value = "/login")
    public String getLogin() {
        return Pages.LOGIN_PAGE;
    }
}
