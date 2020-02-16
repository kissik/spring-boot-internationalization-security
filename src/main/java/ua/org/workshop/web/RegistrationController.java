package ua.org.workshop.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.domain.Account;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RoleService;
import ua.org.workshop.web.form.AccountForm;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    private static final Logger LOGGER = LogManager.getLogger(RegistrationController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields(
                "username",
                "password",
                "confirmPassword",
                "firstName",
                "lastName",
                "firstNameOrigin",
                "lastNameOrigin",
                "email",
                "phone");
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getRegistrationForm(Model model) {
        AccountForm accountForm = new AccountForm();
        accountForm.setRole(new String[]{ApplicationConstants.APP_DEFAULT_ROLE});
        model.addAttribute(
                ApplicationConstants.ModelAttribute.Form.ACCOUNT_FORM,
                accountForm);
        return Pages.REGISTRATION_FORM_PAGE;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postRegistrationForm(
            @ModelAttribute(ApplicationConstants.ModelAttribute.Form.ACCOUNT_FORM) @Valid AccountForm form,
            BindingResult result, Model model) {

        convertError(result);

        validateUsername(form.getUsername(), result);
        validateEmail(form.getUsername(), form.getEmail(), result);
        validatePhone(form.getUsername(), form.getPhone(), result);

        Account account = toAccount(form);
        account.setRoles(Collections.singletonList(roleService.findByCode(ApplicationConstants.APP_DEFAULT_ROLE)));
        account.setPassword(passwordEncoder.encode(form.getPassword()));

        if (!result.hasErrors())
            accountService.registerAccount(account);

        model.addAttribute(
                ApplicationConstants.ModelAttribute.Form.LOGIN_USERNAME,
                form.getUsername());

        return (result.hasErrors() ? Pages.REGISTRATION_FORM_PAGE : Pages.REGISTRATION_FORM_REDIRECT_SUCCESS);
    }

    private static void convertError(BindingResult result) {
        for (ObjectError error : result.getGlobalErrors()) {
            String msg = error.getDefaultMessage();
            if ("account.password.mismatch.message".equals(msg) && !result.hasFieldErrors("password"))
                result.rejectValue("password", "error.password", new String[]{"error.password"}, "Mismatch passwords!");
        }
    }

    private void validateUsername(String username, Errors errors) {
        try {
            accountService.getAccountByUsername(username);
            errors.rejectValue("username", "error.duplicate", new String[]{username}, "Login is already in use!");
            LOGGER.info("Validation failed: duplicate username -> " + username);
        }catch(WorkshopException ignored){
        }
    }

    private void validateEmail(String username, String email, Errors errors) {
        try{
            accountService.getAccountByEmail(email);
            LOGGER.debug("Validation failed: duplicate email -> " + email);
            errors.rejectValue("email", "error.duplicate", new String[]{email}, "email is already in use!");
        }
        catch(WorkshopException ignored){
        }
    }

    private void validatePhone(String username, String phone, Errors errors) {
        try{
            accountService.getAccountByPhone(phone);
            LOGGER.debug("Validation failed: duplicate phone -> " + phone);
            errors.rejectValue("phone", "error.duplicate", new String[]{phone}, "The phone already in use!");
        }catch(WorkshopException ignored){
        }
    }

    private static Account toAccount(AccountForm form) {
        Account account = new Account();
        account.setUsername(form.getUsername());
        account.setFirstName(form.getFirstName());
        account.setLastName(form.getLastName());
        account.setFirstNameOrigin(form.getFirstNameOrigin());
        account.setLastNameOrigin(form.getLastNameOrigin());
        account.setEmail(form.getEmail());
        account.setPhone(form.getPhone());
        account.setEnabled(true);
        return account;
    }
}
