package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kissik
 */
@Controller
public class PortalController {

    private static final Logger logger = LogManager.getLogger(PortalController.class);

    @RequestMapping(value = "/access-denied")
    public String getAccessDenied() {
        return "access-denied";
    }

    @RequestMapping(value = "/login")
    public String getLogin(Model model, HttpServletRequest req, HttpServletResponse res) {
        try {
            if (req.getQueryString().contains("error"))
                model.addAttribute("error", "true");
        }
        catch(Exception e){
            logger.error("login error");
            logger.info("login error");
        }
        return "login";
    }
}
