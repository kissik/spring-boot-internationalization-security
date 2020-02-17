package ua.org.workshop.exception;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.org.workshop.domain.Role;
import ua.org.workshop.domain.Status;
import ua.org.workshop.repository.AccountRepository;
import ua.org.workshop.repository.RequestRepository;
import ua.org.workshop.repository.RoleRepository;
import ua.org.workshop.repository.StatusRepository;
import ua.org.workshop.service.AccountService;
import ua.org.workshop.service.RequestService;
import ua.org.workshop.service.RoleService;
import ua.org.workshop.service.StatusService;

import java.util.ArrayList;
import java.util.List;

public class StatusServiceExceptionTest {

    @InjectMocks
    private StatusService statusService;

    private BeforeServiceExceptionTest beforeServiceExceptionTest = new BeforeServiceExceptionTest();
    private List<Status> statuses = new ArrayList<>();

    @Before
    public void testInit() {
        MockitoAnnotations.initMocks(this);
        beforeServiceExceptionTest.initStatusList(statuses);
    }

    @Test(expected = WorkshopException.class)
    public void hasNextStatusShouldThrowAnException_For_NextStatusCheckError(){
        statusService.hasNextStatus(
                beforeServiceExceptionTest.getStatusByCode("DONE", statuses),
                beforeServiceExceptionTest.getStatusByCode("REGISTER", statuses)
        );
    }

    @Test(expected = WorkshopException.class)
    public void hasNextStatusShouldThrowAnExceptionToo_For_NextStatusCheckError(){
        statusService.hasNextStatus(
                beforeServiceExceptionTest.getStatusByCode("REGISTER", statuses),
                beforeServiceExceptionTest.getStatusByCode("DONE", statuses)
        );
    }

    @Test
    public void hasNextStatusShouldNotThrowAnException(){
        statusService.hasNextStatus(
                beforeServiceExceptionTest.getStatusByCode("REGISTER", statuses),
                beforeServiceExceptionTest.getStatusByCode("ACCEPT", statuses)
        );
    }

}
