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
import ua.org.workshop.domain.Role;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.service.RoleService;
import ua.org.workshop.service.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/* TODO all Map without Object ! */

@Controller
public class WelcomeController {

	private static final String VN_REG_FORM = "users/registration-form";
	private static final String VN_REG_OK = "redirect:/login";
	private static final String VN_EDIT_OK = "redirect:/users/{id}?saved=true";
	private static final String DEFAULT_ROLE = "USER";

    private static final Logger logger = LogManager.getLogger(WelcomeController.class);

	@Autowired
	private AccountService accountService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleService roleService;

	public class RoleForm{
		private String[] role = {"ROLE_USER"};

		@NotNull(message = "Choose at least one role!")
		public String[] getRole() { return role; }

		public void setRole(String[] role) { this.role = role; }

		public String toString() {
			return new StringBuilder()
					.append(" roles:" + Arrays.toString(role))
					.toString();
		}
	}

	@GetMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", null);
		return "welcome";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String getRegistrationForm(Map<String, Object> model) {
		AccountForm accountForm = new AccountForm();
		accountForm.setRole(new String[] {DEFAULT_ROLE});
		model.put("account", accountForm);
		model.put("roleList", this.roleService.findAll());
		model.put("method", "post");

		return VN_REG_FORM;
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
		account.setRoles(Collections.singletonList(roleService.findByCode(DEFAULT_ROLE)));
		account.setPassword(passwordEncoder.encode(form.getPassword()));

		if (!result.hasErrors())
			try {
				accountService.registerAccount(account);
			}catch (WorkshopException e){
				logger.info("New account error: "+ e.getMessage());
				logger.error("New account error: "+ e.getMessage());
			}

		model.addAttribute("roleList", this.roleService.findAll());
		model.addAttribute("username", form.getUsername());
        model.addAttribute("method", "post");

		return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
	}

	@RequestMapping(value = "/users/{id}/edit", method = RequestMethod.GET)
	public String getEditAccountRoleForm(
			@PathVariable("id") Long id,
			Model model) {
		try{
			model.addAttribute("roleList", this.roleService.findAll());
			logger.info("role list: " + this.roleService.findAll().toString());
			Account account = accountService.getAccountById(id);
			model.addAttribute("originalAccount", account);
			logger.info("originalAccount:" + account.toString());
			RoleForm roleForm = new RoleForm();
			String roles = "";
			for(Role r : account.getRoles()) roles=roles + r.getCode() +" ";
			roleForm.setRole(roles.split(" "));
			model.addAttribute("roles", roleForm);
			logger.info("form roles:" + roleForm.toString());
		}catch(IllegalArgumentException e){
			model.addAttribute("error", e.getMessage());
			return "access-denied";
		}

		return "users/edit-user-form";
	}

	@RequestMapping(value = "/users/{id}/edit", method = RequestMethod.POST)
	public String putAccountRoles(
			@PathVariable("id") Long id,
			@ModelAttribute("roles") @Valid RoleForm roleForm,
			BindingResult result,
			Map<String, Object> model) {

		Account accountOrigin;
		accountOrigin = accountService.getAccountById(id);

		accountService.setAccountInfo(accountOrigin, roleForm.getRole());

		model.put("username", accountOrigin.getUsername());
		return VN_EDIT_OK;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields("username", "password", "confirmPassword", "firstName", "lastName",
				"firstNameOrigin", "lastNameOrigin",
				"email","phone","role");
	}

	private static void convertError(BindingResult result) {
		for (ObjectError error : result.getGlobalErrors()) {
			String msg = error.getDefaultMessage();
			if ("account.password.mismatch.message".equals(msg)) {
				if (!result.hasFieldErrors("password")) {
					result.rejectValue("password", "error.password", new String[] { "error.password" }, "Mismatch passwords!");
				}
			}
			if ("account.role.isnull.message".equals(msg)) {
				if (!result.hasFieldErrors("role")) {
					result.rejectValue("role", "error.role", new String[] { "error.role" }, "Add one role at least!");
				}
			}
		}
	}

	private void validateUsername(String username, Errors errors) {
		try {
			accountService.getAccountByUsername(username);
		}
		catch (WorkshopException e){
			logger.error("error: " + e.getMessage());
			logger.info("error: " + e.getMessage());
			return;
		}
		errors.rejectValue("username", "error.duplicate", new String[]{username}, "Login is already in use!");
		logger.info("Validation failed: duplicate username -> " + username);
	}

	private void validateEmail(String username, String email, Errors errors) {
		try {
			accountService.getAccountByEmail(email);
		}
		catch (WorkshopException e){
			logger.error("error: " + e.getMessage());
			logger.info("error: " + e.getMessage());
			return;
		}
		logger.debug("Validation failed: duplicate email -> " + email);
		errors.rejectValue("email", "error.duplicate", new String[]{email}, "email is already in use!");
	}

	private void validatePhone(String username, String phone, Errors errors) {
		try{
			accountService.getAccountByPhone(phone);
		}
		catch (WorkshopException e){
			logger.error("error: " + e.getMessage());
			logger.info("error: " + e.getMessage());
			return;
		}
		logger.debug("Validation failed: duplicate phone -> " + phone);
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
