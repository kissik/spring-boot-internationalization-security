package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.AccountService;

@Controller
public class AccountController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Model model) {
        try{
            model.addAttribute("usersList", accountService.getAccountList());
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "users/users-list";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String getAccount(@PathVariable("id") Long id, Model model) {
        try{
            model.addAttribute("account", accountService.getAccountById(id));
        }catch(WorkshopException e){
            logger.info("custom error message: " + e.getMessage());
            logger.error("custom error message: " + e.getMessage());
            model.addAttribute("message", e.getMessage());
            return "error";
        }
        return "users/user";
    }

}
