package ua.org.workshop.web;

public interface Pages {

    String REDIRECT = "redirect:";

    String ACCESS_DENIED_PAGE = "access-denied";
    String ACCESS_DENIED_PAGE_REDIRECT =
            REDIRECT + "/access-denied";

    String ADMIN_PAGE = "admin/page";
    String ADMIN_PAGE_REDIRECT =
            REDIRECT + "/admin/page";
    String ADMIN_PAGE_REDIRECT_DELETE_ACCOUNT_FAILED =
            REDIRECT + "/admin/page?deleted=false";
    String ADMIN_PAGE_REDIRECT_DELETE_ACCOUNT_SUCCESS =
            REDIRECT + "/admin/page?deleted=true";
    String ADMIN_PAGE_REDIRECT_UPDATE_ACCOUNT_SUCCESS =
            REDIRECT + "/admin/accounts/";
    String ADMIN_ACCOUNT_INFO_PAGE = "admin/user";
    String ADMIN_ACCOUNT_EDIT_PAGE = "admin/edit-user-form";

    String ERROR_PAGE = "/error";

    String FIRST_PAGE = REDIRECT + "/";

    String LOGIN_PAGE = "login";

    String MANAGER_PAGE = "manager/page";
    String MANAGER_PAGE_REDIRECT =
            REDIRECT + "/manager/page";
    String MANAGER_PAGE_REDIRECT_UPDATE_REQUEST_SUCCESS =
            REDIRECT + "/manager/page?updated=true";
    String MANAGER_REQUEST_PAGE = "manager/request";
    String MANAGER_UPDATE_REQUEST_FORM_PAGE = "manager/edit-request-form";

    String REGISTRATION_FORM_REDIRECT_SUCCESS =
            REDIRECT + "/login";
    String REGISTRATION_FORM_PAGE = "registration-form";

    String USER_CREATE_REQUEST_FORM = "user/create-request-form";
    String USER_PAGE = "user/page";
    String USER_PAGE_REDIRECT =
            REDIRECT + "/user/page";
    String USER_PAGE_REDIRECT_NEW_REQUEST_SUCCESS =
            REDIRECT + "/user/page?created=true";
    String USER_PAGE_REDIRECT_UPDATE_HISTORY_REQUEST_SUCCESS =
            REDIRECT + "/user/page?updated=true";

    String WORKMAN_PAGE = "workman/page";
    String WORKMAN_PAGE_REDIRECT =
            REDIRECT + "/workman/page";
    String WORKMAN_PAGE_REDIRECT_UPDATE_REQUEST_SUCCESS =
            REDIRECT + "/workman/page?updated=true";
    String WORKMAN_REQUEST_PAGE = "workman/request";
    String WORKMAN_UPDATE_REQUEST_FORM_PAGE = "workman/edit-request-form";

}
