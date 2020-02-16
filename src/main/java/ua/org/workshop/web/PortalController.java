package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author kissik
 */
@Controller
public class PortalController {
    private static final Logger LOGGER = LogManager.getLogger(AdminRoleController.class);

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
        LOGGER.info("This will throw a NullPointerException");

        @SuppressWarnings("null")
        String str = null;
        str.length();

        return Pages.ERROR_PAGE;
    }
}
