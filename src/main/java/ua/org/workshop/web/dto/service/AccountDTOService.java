package ua.org.workshop.web.dto.service;

import org.springframework.context.MessageSource;
import ua.org.workshop.domain.Account;
import ua.org.workshop.web.dto.AccountDTO;

public class AccountDTOService {
    private final MessageSource messageSource;

    public AccountDTOService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public AccountDTO getAccountDTO(Account account) {
        return new AccountDTO(account);
    }
}
