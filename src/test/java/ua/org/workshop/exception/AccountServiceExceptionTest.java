package ua.org.workshop.exception;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.domain.Account;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.repository.AccountRepository;
import ua.org.workshop.repository.RoleRepository;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RoleService;

import static org.mockito.Mockito.*;

public class AccountServiceExceptionTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private AccountService accountService;

    @Before
    public void testInit() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = WorkshopException.class)
    public void getAccountByIdShouldThrowAnException_For_AccountNotFound(){
        Long id = 234567789L;

        when(accountService.getAccountById(id)).thenThrow(new WorkshopException(WorkshopError.ACCOUNT_NOT_FOUND_ERROR));

        Assert.assertEquals(accountService.getAccountById(id).getId(), id);
    }
}
