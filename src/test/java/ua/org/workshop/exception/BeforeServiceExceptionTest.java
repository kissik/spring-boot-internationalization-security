package ua.org.workshop.exception;

import ua.org.workshop.domain.Role;
import ua.org.workshop.domain.Status;

import java.util.ArrayList;
import java.util.List;

public class BeforeServiceExceptionTest {

    void initStatusList(List<Status> statuses){

        List<Status> nextStatuseslist = new ArrayList<>();
        Status done = createStatus("DONE","done", nextStatuseslist, true);
        statuses.add(done);
        Status reject = createStatus("REJECT","reject", nextStatuseslist, true);
        statuses.add(reject);
        nextStatuseslist.add(done);
        Status accept = createStatus("ACCEPT","accept", nextStatuseslist, false);
        statuses.add(accept);
        nextStatuseslist.clear();
        nextStatuseslist.add(accept);
        nextStatuseslist.add(reject);
        Status register = createStatus("REGISTER","register", nextStatuseslist, false);
        statuses.add(register);
    }

    Status createStatus(
            String code,
            String name,
            List<Status> nextStatuslist,
            boolean closed){
        Status status = new Status();
        status.setCode(code);
        status.setName(name);
        status.setNextStatuses(nextStatuslist);
        status.setClosed(closed);
        return status;
    }

    Status getStatusByCode(String code, List<Status> statuses){
        for (Status status : statuses){
            if (status.getCode().equals(code)) return status;
        }
        return null;
    }

    void initRolesList(List<Role> roles) {
        roles.add(createRole("ADMIN", "Administrator"));
        roles.add(createRole("MANAGER", "Manager"));
        roles.add(createRole("WORKMAN", "Workman"));
        roles.add(createRole("USER", "User"));
    }

    private Role createRole(String code, String name){
        Role role = new Role();
        role.setCode(code);
        role.setName(name);
        return role;
    }

}
