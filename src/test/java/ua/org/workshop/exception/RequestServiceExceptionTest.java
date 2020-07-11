package ua.org.workshop.exception;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;
import ua.org.workshop.domain.Request;
import ua.org.workshop.domain.Role;
import ua.org.workshop.domain.Status;
import ua.org.workshop.repository.AccountRepository;
import ua.org.workshop.repository.RequestRepository;
import ua.org.workshop.repository.RoleRepository;
import ua.org.workshop.repository.StatusRepository;
import ua.org.workshop.service.*;
import ua.org.workshop.web.WorkmanRoleController;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(WorkmanRoleController.class)
public class RequestServiceExceptionTest {
    private static final String CURRENT_ROLE = "WORKMAN";

    @Mock
    private RoleRepository roleRepository;
    @Mock
    private StatusRepository statusRepository;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private AccountRepository accountRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext wac;

    @InjectMocks
    private RoleService roleService;
    @InjectMocks
    private RequestService requestService;
    @InjectMocks
    private StatusService statusService;
    @InjectMocks
    private AccountService accountService;
    @InjectMocks
    private SecurityService securityService;

    private BeforeServiceExceptionTest beforeServiceExceptionTest = new BeforeServiceExceptionTest();

    private List<Role> roles = new ArrayList<>();
    private List<Status> statuses = new ArrayList<>();

    @Before
    public void testInit() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity()).build();
        MockitoAnnotations.initMocks(this);
        beforeServiceExceptionTest.initRolesList(roles);
        beforeServiceExceptionTest.initStatusList(statuses);

//        applicationContext = new AnnotationConfigReactiveWebApplicationContext(WebSecurityConfig.class);
//        userDetailsService = applicationContext.getBean(AccountDetailsService.class);
    }

    @Test(expected = WorkshopException.class)
    public void editWorkmanRequestStatusShouldThrowAnException_For_RightViolationError() throws Exception {
        Request request = new Request();
        Long id = 1L;
        request.setStatus(beforeServiceExceptionTest.getStatusByCode("REGISTER", statuses));
        when(SecurityService.isCurrentUserHasRole(CURRENT_ROLE)).thenReturn(true);
        when(requestService.findById(id)).thenReturn(request);

        mockMvc.perform(post("/request/"+id+"/edit",null));
    }
}
