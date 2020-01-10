package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.org.workshop.domain.Account;
import ua.org.workshop.service.AccountService;

import java.util.Map;
import java.util.Optional;

@Controller
public class AccountController {
    private static final Logger logger = LogManager.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUsers(Map<String, Object> model) {
        model.put("usersList",this.accountService.getAccountList());
        return "users/users-list";
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String getAccount(@PathVariable("id") Long id, Map<String, Object> model) throws IllegalArgumentException{
        try{
            Optional<Account> account = accountService.getAccountById(id);
            model.put("account", account.get());
        }catch(IllegalArgumentException e){
            model.put("error", e.getMessage());
            return "access-denied";
        }
        return "users/user";
    }

}
