package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.org.workshop.domain.Account;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.repository.AccountRepository;

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

    private static final Logger LOGGER = LogManager.getLogger(AccountService.class);

    @Transactional(readOnly = false)
    public boolean registerAccount(Account account) throws WorkshopException {
        try {
            accountRepository.save(account);
        }catch (Exception e){
            LOGGER.error("error: " + e.getMessage());
            throw new WorkshopException(WorkshopError.ACCOUNT_CREATE_NEW_ERROR);
        }
        LOGGER.info("Account " + account.getFullNameOrigin() + " was successfully created");
        return true;
    }

    @Transactional(readOnly = false)
    public void update(Account account) {
        try{
            accountRepository.saveAndFlush(account);
        }catch (Exception e){
            LOGGER.error("error: " + e.getMessage());
            throw new WorkshopException(WorkshopError.ACCOUNT_UPDATE_ERROR);
        }
        LOGGER.info("Account " + account.getUsername() + " was successfully updated");
    }

    @Transactional(readOnly = false)
    public void delete(Long id) throws WorkshopException {
        try {
            accountRepository.deleteById(id);
        }
        catch(Exception e){
            LOGGER.error("delete account error : " + e.getMessage());
            throw new WorkshopException(WorkshopError.ACCOUNT_DELETE_ERROR);
        }
    }

    public Account getAccountByUsername(String username) throws WorkshopException {
        Account account = accountRepository
                .findByUsername(username)
                .orElseThrow(() -> new WorkshopException(WorkshopError.ACCOUNT_NOT_FOUND_ERROR));
        Hibernate.initialize(account.getRoles());
        return account;
    }

    public Account getAccountByPhone(String phone) throws WorkshopException {
        return accountRepository
                .findByPhone(phone)
                .orElseThrow(() -> new WorkshopException(WorkshopError.ACCOUNT_NOT_FOUND_ERROR));
    }

    public Account getAccountByEmail(String email) throws WorkshopException {
        return accountRepository
                .findByEmail(email)
                .orElseThrow(() -> new WorkshopException(WorkshopError.ACCOUNT_NOT_FOUND_ERROR));
    }

    public Account getAccountById(Long id) throws WorkshopException {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new WorkshopException(WorkshopError.ACCOUNT_NOT_FOUND_ERROR));
    }

    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }
}