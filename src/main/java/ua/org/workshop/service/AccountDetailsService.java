package ua.org.workshop.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.workshop.dao.AccountDetails;
import ua.org.workshop.domain.Account;
import ua.org.workshop.exception.WorkshopException;

@Service
@Transactional(readOnly = true)
public class AccountDetailsService implements UserDetailsService {

    private final AccountService accountService;
    private static final Logger LOGGER = LogManager.getLogger(AccountDetailsService.class);

    public AccountDetailsService(AccountService accountService){
        super();
        this.accountService = accountService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account;
        try {
            account = accountService.getAccountByUsername(username);
        }
        catch(WorkshopException e){
            LOGGER.error("cannot find username: " + username);
            throw new UsernameNotFoundException("cannot find username: " + username);
        }
        LOGGER.info("loaded account : " + account.getUsername());
        Hibernate.initialize(account.getRoles());
        return new AccountDetails(account);
    }
}
