package ua.org.workshop;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ua.org.workshop.domain.Account;
import ua.org.workshop.domain.Role;
import ua.org.workshop.repository.AccountRepository;
import ua.org.workshop.repository.RoleRepository;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RoleService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.lenient;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceIntegrationTest {

    private Long id = 0L;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private RoleService roleService;

    @InjectMocks
    private AccountService accountService;

    private Map<String, String> roles = new HashMap<>();

    @Before
    public void testInit() {
        MockitoAnnotations.initMocks(this);
        initRolesMap();
    }

    private void initRolesMap() {
        roles.put("ADMIN", "Administrator");
        roles.put("MANAGER", "Manager");
        roles.put("WORKMAN", "Workman");
        roles.put("USER", "User");
    }

    private void createRole(String code, String name) {
        Role role = new Role();
        role.setId(++id);
        role.setCode(code);
        role.setName(name);

        lenient().when(roleService.newRole(role))
                .thenReturn(true);

    }

    @Test
    public void testRoleDB() {
        roles.forEach(this::createRole);
    }

    @Test
    public void testAddAccountDB() {
        Role role = new Role();
        role.setId(1L);
        role.setCode("ADMIN");
        role.setName("Administrator");

        Account account = new Account();
        account.setFirstName("Iryna");
        account.setFirstNameOrigin("Ірина");
        account.setLastName("Afanasieva");
        account.setFirstNameOrigin("Афанасьєва");
        account.setEmail("iryna.v.afanasieva@gmail.com");
        account.setPhone("+38(050)145-72-54");
        account.setEnabled(true);
        account.setUsername("kissik");
        account.setRoles(Arrays.asList(role));
        account.setPassword("password");
        account.setDateCreated(LocalDate.now());

        lenient().when(accountService.registerAccount(account))
                .thenReturn(true);

    }
}
