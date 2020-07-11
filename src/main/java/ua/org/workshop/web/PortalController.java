package ua.org.workshop.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kissik
 */
@Slf4j
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

    @RequestMapping(value = "/null-pointer", method = RequestMethod.GET)
    public String throwNullPointerException() {
        log.info("This will throw a NullPointerException");

        @SuppressWarnings("null")
        String str = null;
        str.length();

        return Pages.ERROR_PAGE;
    }
}
