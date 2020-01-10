package ua.org.workshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kissik
 */
@Controller
public class PortalController {

    // Use this instead of <mvc:view-controller> so we can handle all HTTP methods and not just GET. (Forwarding to the
    // access denied page preserves the HTTP request method.)
    @RequestMapping(value = "/access-denied.html")
    public String getAccessDenied() { return "access-denied"; }

    @GetMapping(value = "/login")
    public String getLogin(){
        return "login";
    }
}
