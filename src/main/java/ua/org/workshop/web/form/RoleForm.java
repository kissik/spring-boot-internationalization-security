package ua.org.workshop.web.form;

import ua.org.workshop.service.ApplicationConstants;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

public class RoleForm {
    private String[] role = {ApplicationConstants.APP_DEFAULT_ROLE};

    @NotNull(message = "{validation.text.error.at.least.one}")
    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public String toString() {
        return " roles : " + Arrays.toString(role);
    }
}
