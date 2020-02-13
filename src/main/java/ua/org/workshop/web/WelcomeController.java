package ua.org.workshop.web;

import java.util.*;

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
import org.springframework.web.bind.annotation.*;
import ua.org.workshop.domain.Account;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.ApplicationConstants;
import ua.org.workshop.service.RoleService;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.web.form.AccountForm;

import javax.validation.Valid;

@Controller
public class WelcomeController {

    private static final Logger LOGGER = LogManager.getLogger(WelcomeController.class);

	@Autowired
	private AccountService accountService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/")
	public String welcome() {
		return "welcome";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		AccountForm accountForm = new AccountForm();
		accountForm.setRole(new String[] {ApplicationConstants.APP_DEFAULT_ROLE});
		model.addAttribute("account", accountForm);

		return Pages.REGISTRATION_FORM_PAGE;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("account") @Valid AccountForm form,
			BindingResult result, Model model) {

		convertError(result);

		validateUsername(form.getUsername(),result);
		validateEmail(form.getUsername(),form.getEmail(),result);
		validatePhone(form.getUsername(),form.getPhone(),result);

		Account account = toAccount(form);
		account.setRoles(Collections.singletonList(roleService.findByCode(ApplicationConstants.APP_DEFAULT_ROLE)));
		account.setPassword(passwordEncoder.encode(form.getPassword()));

		if (!result.hasErrors())
			try {
				accountService.registerAccount(account);
			}catch (WorkshopException e){
				LOGGER.info("New account error: "+ e.getMessage());
				LOGGER.error("New account error: "+ e.getMessage());
			}

		model.addAttribute("username", form.getUsername());

		return (result.hasErrors() ? Pages.REGISTRATION_FORM_PAGE : Pages.REGISTRATION_FORM_OK);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("username", "password", "confirmPassword", "firstName", "lastName",
				"firstNameOrigin", "lastNameOrigin",
				"email","phone");
	}

	private static void convertError(BindingResult result) {
		for (ObjectError error : result.getGlobalErrors()) {
			String msg = error.getDefaultMessage();
			if ("account.password.mismatch.message".equals(msg) && !result.hasFieldErrors("password"))
				result.rejectValue("password", "error.password", new String[] { "error.password" }, "Mismatch passwords!");
		}
	}

	private void validateUsername(String username, Errors errors) {
		try {
			accountService.getAccountByUsername(username);
		}
		catch (WorkshopException e){
			LOGGER.error("error: " + e.getMessage());
			return;
		}
		errors.rejectValue("username", "error.duplicate", new String[]{username}, "Login is already in use!");
		LOGGER.info("Validation failed: duplicate username -> " + username);
	}

	private void validateEmail(String username, String email, Errors errors) {
		try {
			accountService.getAccountByEmail(email);
		}
		catch (WorkshopException e){
			LOGGER.error("error: " + e.getMessage());
			return;
		}
		LOGGER.debug("Validation failed: duplicate email -> " + email);
		errors.rejectValue("email", "error.duplicate", new String[]{email}, "email is already in use!");
	}

	private void validatePhone(String username, String phone, Errors errors) {
		try{
			accountService.getAccountByPhone(phone);
		}
		catch (WorkshopException e){
			LOGGER.error("error: " + e.getMessage());
			return;
		}
		LOGGER.debug("Validation failed: duplicate phone -> " + phone);
		errors.rejectValue("phone", "error.duplicate", new String[]{phone}, "The phone already in use!");
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
