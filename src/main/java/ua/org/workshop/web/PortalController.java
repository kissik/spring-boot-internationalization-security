package ua.org.workshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kissik
 */
@Controller
public class PortalController {

    @RequestMapping(value = "/access-denied")
    public String getAccessDenied() {
        return "access-denied";
    }

    @GetMapping(value = "/login")
    public String getLogin(){
        return "login";
    }
}
