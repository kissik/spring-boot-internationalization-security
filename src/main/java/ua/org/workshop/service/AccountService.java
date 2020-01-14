package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional(readOnly = false)
    public void registerAccount(Account account) throws WorkshopException {

        try {
            accountRepository.save(account);
        }catch (Exception e){
            logger.error("error: " + e.getMessage());
            logger.info("error: " + e.getMessage());
            throw new WorkshopException(WorkshopErrors.ACCOUNT_CREATE_NEW_ERROR);
        }
        logger.info("Account " + account.getFullNameOrigin() + " was successfully created");
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

    @Transactional(readOnly = false)
    public boolean newAccount(Account account) throws WorkshopException {

        try{
            accountRepository.save(account);
        }catch(Exception e){
            logger.error("error: " + e.getMessage());
            logger.info("error: " + e.getMessage());
            throw new WorkshopException(WorkshopErrors.ACCOUNT_CREATE_NEW_ERROR);
        }
        return true;
    }

    public Account getAccountByUsername(String username) throws WorkshopException {
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.ACCOUNT_NOT_FOUND_ERROR));
        Hibernate.initialize(account.getRoles());
        return account;
    }

    public Account getAccountByPhone(String phone) throws WorkshopException {
        return accountRepository
                .findByPhone(phone)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.ACCOUNT_NOT_FOUND_ERROR));
    }

    public Account getAccountByEmail(String email) throws WorkshopException {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new WorkshopException(WorkshopErrors.ACCOUNT_NOT_FOUND_ERROR));
    }
}