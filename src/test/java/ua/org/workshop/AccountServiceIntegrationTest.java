package ua.org.workshop;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.Errors;
import ua.org.workshop.domain.Account;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RoleService;

import java.time.LocalDate;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AccountServiceIntegrationTest {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testAddAccountDB(){
        Error errors = new Error();
        Account account = new Account();
        account.setFirstName("Iryna");
        account.setFirstNameOrigin("Ірина");
        account.setLastName("Afanasieva");
        account.setFirstNameOrigin("Афанасьєва");
        account.setEmail("iryna.v.afanasieva@gmail.com");
        account.setPhone("+38(050)145-72-54");
        account.setEnabled(true);
        account.setUsername("kissik");
        account.setDateCreated(LocalDate.now());

        accountService.registerAccount(
                account,
                new String[]{roleService.getRoleRepository().findByCode("ADMIN").getCode()},
                passwordEncoder.encode("p4$$w0rd"),
                (Errors) errors);
        Account newAccount = accountService.getAccountByUsername("kissik");

        assertNotNull(newAccount);

    }
}
