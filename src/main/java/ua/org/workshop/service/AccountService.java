package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Role;
import ua.org.workshop.exception.WorkshopErrors;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.AccountRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * @author kissik
 */
@Service
@Transactional(readOnly = true)
public class AccountService{

    @Autowired
    private RoleService roleService;

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        super();
        this.accountRepository = accountRepository;
    }

    private static final Logger logger = LogManager.getLogger(AccountService.class);
    /* TODO try catch VS valid form */
    @Transactional(readOnly = false)
    public boolean registerAccount(Account account, String[] roleForm, String password, Errors errors) {
        validateUsername(account.getUsername(), errors);
        validateEmail(account.getUsername(), account.getEmail(), errors);
        validatePhone(account.getUsername(), account.getPhone(), errors);
        logger.info("errors " + (!errors.hasErrors() ? "none" : errors.toString()));
        account.setPassword(password);
        boolean valid = !errors.hasErrors();

        if (valid) {
            Collection<Role> roles = new HashSet<>();
            for (String roleStr : roleForm) roles.add(roleService.findByCode(roleStr));
            account.setRoles(roles);
            accountRepository.save(account);
        }
        logger.info(account.getFullNameOrigin());
        return valid;
    }

    private void validateUsername(String username, Errors errors) {
        try {
            accountRepository.findByUsername(username);
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
        List<Account> accountList = this.getAccountList();
        boolean check = false;
        for (Account a : accountList) {
            if ((!a.getUsername().equals(username)) && (a.getEmail().equals(email))) check = true;
        }
        if (check) {
            logger.debug("Validation failed: duplicate email -> " + email);
            errors.rejectValue("email", "error.duplicate", new String[]{email}, "email is already in use!");
        }
    }

    private void validatePhone(String username, String phone, Errors errors) {
        List<Account> accountList = this.getAccountList();
        boolean check = false;
        for (Account a : accountList) {
            if ((!a.getUsername().equals(username)) && (a.getPhone().equals(phone))) check = true;
        }
        if (check) {
            logger.debug("Validation failed: duplicate phone -> " + phone);
            errors.rejectValue("phone", "error.duplicate", new String[]{phone}, "The phone already in use!");
        }
    }

    public List<Account> getAccountList() throws WorkshopException {

        List<Account> accountList = accountRepository
                .findAll();
        if (accountList == null) {
            throw new WorkshopException(WorkshopErrors.ACCOUNT_LIST_IS_EMPTY_ERROR);
        }
        return accountList;
    }

    public Account getAccountById(Long id) throws WorkshopException {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.ACCOUNT_NOT_FOUND_ERROR));
    }

    @Transactional(readOnly = false)
    public void setAccountInfo(Account account, String[] roleForm) {

            Collection<Role> roles = new HashSet<>();
            for (String roleStr : roleForm) roles.add(roleService.findByCode(roleStr));
            account.setRoles(roles);
            accountRepository.saveAndFlush(account);

    }

    public Account getAccountByUsername(String username) throws WorkshopException {
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.ACCOUNT_NOT_FOUND_ERROR));
        Hibernate.initialize(account.getRoles());
        return account;
    }
}