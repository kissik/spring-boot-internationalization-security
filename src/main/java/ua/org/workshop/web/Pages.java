package ua.org.workshop.web;

public interface Pages {

    String ACCESS_DENIED_PAGE = "access-denied";

    String ADMIN_PAGE = "jsp/admin/page";
    String ADMIN_PAGE_REDIRECT = "redirect:/admin/page";
    String ADMIN_PAGE_REDIRECT_DELETE_ACCOUNT_FAILED = "redirect:/admin/page?deleted=false";
    String ADMIN_PAGE_REDIRECT_DELETE_ACCOUNT_SUCCESS = "redirect:/admin/page?deleted=true";

    String FIRST_PAGE = "redirect:/";

    String LOGIN_PAGE = "login";

    String MANAGER_PAGE = "jsp/manager/page";
    String MANAGER_PAGE_REDIRECT = "redirect:/manager/page";
    String MANAGER_PAGE_REDIRECT_UPDATE_REQUEST_SUCCESS = "redirect:/manager/page?updated=true";;

    String REGISTRATION_FORM_OK = "redirect:/login";
    String REGISTRATION_FORM_PAGE = "registration-form";

    String USER_INFO_PAGE = "jsp/admin/user";
    String USER_EDIT_PAGE = "jsp/admin/edit-user-form";
    String USER_PAGE = "jsp/user/page";
    String USER_PAGE_REDIRECT = "redirect:/user/page";
    String USER_PAGE_REDIRECT_NEW_REQUEST_SUCCESS = "redirect:/user/page?created=true";
    String USER_PAGE_REDIRECT_UPDATE_HISTORY_REQUEST_SUCCESS = "redirect:/user/page?updated=true";

    String WORKMAN_PAGE = "jsp/workman/page";
    String WORKMAN_PAGE_REDIRECT = "redirect:/workman/page";
    String WORKMAN_PAGE_REDIRECT_UPDATE_REQUEST_SUCCESS = "redirect:/workman/page?updated=true";

}
