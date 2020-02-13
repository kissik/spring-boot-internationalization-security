package ua.org.workshop.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.org.workshop.dao.AccountDetails;

public class SecurityService {

    public static boolean isCurrentUserHasRole(String role){
        return getPrincipal().hasRole(role);
    }
    private static Authentication getAuthentication(){
        SecurityContext securityCtx = SecurityContextHolder.getContext();
        return securityCtx.getAuthentication();
    }
    private static AccountDetails getPrincipal(){
        return (AccountDetails) getAuthentication().getPrincipal();
    }
    public static String getCurrentUsername() {
        return getPrincipal().getUsername();
    }
}
