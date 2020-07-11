package ua.org.workshop.service;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
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
@Slf4j
@Service
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;
    private final RoleService roleService;

    public AccountService(AccountRepository accountRepository,
                          RoleService roleService) {
        this.accountRepository = accountRepository;
        this.roleService = roleService;
    }

    @Transactional(readOnly = false)
    public boolean registerAccount(Account account) throws WorkshopException {
        try {
            accountRepository.save(account);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            throw new WorkshopException(WorkshopError.ACCOUNT_CREATE_NEW_ERROR);
        }
        log.info("Account {} was successfully created", account.getFullNameOrigin());
        return true;
    }

    @Transactional(readOnly = false)
    public void update(Account account) {
        try {
            accountRepository.saveAndFlush(account);
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
            throw new WorkshopException(WorkshopError.ACCOUNT_UPDATE_ERROR);
        }
        log.info("Account {} was successfully updated", account.getUsername());
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        accountRepository.deleteById(id);
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

    public Page<Account> findAll(Pageable pageable) throws WorkshopException{
        try {
            return accountRepository.findAll(pageable);
        }catch (ConstraintViolationException ex){
            throw new WorkshopException(WorkshopError.ACCOUNT_DELETE_ERROR);
        }
    }
}