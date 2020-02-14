package ua.org.workshop.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.org.workshop.dao.AccountDetails;
import ua.org.workshop.enums.WorkshopError;
import ua.org.workshop.exception.WorkshopException;
import ua.org.workshop.web.Pages;

public class SecurityService {

    public static boolean isCurrentUserHasRole(String role) {
        return getPrincipal().hasRole(role);
    }

    private static Authentication getAuthentication() {
        SecurityContext securityCtx = SecurityContextHolder.getContext();
        return securityCtx.getAuthentication();
    }

    private static AccountDetails getPrincipal() {
        return (AccountDetails) getAuthentication().getPrincipal();
    }

    public static String getCurrentUsername() {
        return getPrincipal().getUsername();
    }

    public static void checkTheAuthorities(String role, String status, String statusCheck) throws WorkshopException {
        boolean check = true;
        if (SecurityService.isCurrentUserHasRole(role) && status.equals(statusCheck))
            check = false;

        if (check) throw new WorkshopException(WorkshopError.RIGHT_VIOLATION_ERROR);
    }

    public static String getPathByAuthority() {

        if (SecurityService.isCurrentUserHasRole("ADMIN")) {
            return Pages.ADMIN_PAGE;
        }
        if (SecurityService.isCurrentUserHasRole("WORKMAN")) {
            return Pages.WORKMAN_PAGE;
        }
        if (SecurityService.isCurrentUserHasRole("MANAGER")) {
            return Pages.MANAGER_PAGE;
        }
        if (SecurityService.isCurrentUserHasRole("USER")) {
            return Pages.USER_PAGE;
        }

        throw new IllegalStateException();

    }
}
