package ua.org.workshop.service;

import java.math.BigDecimal;

public interface ApplicationConstants {

    interface Pageable {
        int PAGE_DEFAULT_VALUE = 0;
        String PAGE_ATTRIBUTE = "page";
        String SEARCH_ATTRIBUTE = "search";
        int SIZE_DEFAULT_VALUE = 5;
        String SIZE_ATTRIBUTE = "size";
        String SORTING_ATTRIBUTE = "sorting";
    }

    interface PathVariable {
        String ID = "id";
    }

    interface ModelAttribute {
        String MANAGER_UPDATE_REQUEST_FORM = "requestForm";
        String WORKMAN_UPDATE_REQUEST_FORM = "requestForm";
        String REQUEST = "request";
        String MESSAGE = "message";
        String ACCOUNT = "account";
        String ROLES_LIST = "rolesList";
        String ROLE = "role";
    }

    interface RequestField {
        String TITLE = "title";
        String DATE_CREATED = "dateCreated";
    }

    interface AccountField{
        String USERNAME = "username";
        String DATE_CREATED = "dateCreated";
    }

    interface RequestAttributes {
        String ACCOUNT_CONFIRM_PASSWORD_ATTRIBUTE = "confirmPassword";
        String ACCOUNT_FIRST_NAME_ATTRIBUTE = "firstName";
        String ACCOUNT_FIRST_NAME_ORIGIN_ATTRIBUTE = "firstNameOrigin";
        String ACCOUNT_EMAIL_ATTRIBUTE = "email";
        String ACCOUNT_ID_ATTRIBUTE = "id";
        String ACCOUNT_LAST_NAME_ATTRIBUTE = "lastName";
        String ACCOUNT_LAST_NAME_ORIGIN_ATTRIBUTE = "lastNameOrigin";
        String ACCOUNT_PHONE_ATTRIBUTE = "phone";
        String APP_ERROR_ATTRIBUTE = "error";
        String APP_LOGGED_USERS_HASH_SET_ATTRIBUTE = "loggedUsers";
        String APP_LANG_ATTRIBUTE = "lang";
        String APP_PASSWORD_ATTRIBUTE = "password";
        String APP_USER_ATTRIBUTE = "user";
        String APP_USERNAME_ATTRIBUTE = "username";
        String HISTORY_REQUEST_ID_ATTRIBUTE = "id";
        String HISTORY_REQUEST_REVIEW_ATTRIBUTE = "review";
        String HISTORY_REQUEST_RATING_ATTRIBUTE = "rating";
        String REQUEST_CAUSE_ATTRIBUTE = "cause";
        String REQUEST_DESCRIPTION_ATTRIBUTE = "description";
        String REQUEST_ID_ATTRIBUTE = "id";
        String REQUEST_PRICE_ATTRIBUTE = "price";
        String REQUEST_STATUS_ATTRIBUTE = "status";
        String REQUEST_TITLE_ATTRIBUTE = "title";

    }

    String APP_ANONYMOUS_ACCOUNT_USERNAME = "anonymous";
    int APP_BCRYPT_SALT = 11;
    String APP_ENCODING = "UTF-8";

    String APP_DEFAULT_LANGUAGE = "en";
    Long APP_DEFAULT_ID = -1L;
    BigDecimal APP_DEFAULT_PRICE = BigDecimal.ZERO;
    BigDecimal APP_DEFAULT_RATING_VALUE = BigDecimal.ONE;

    String APP_MESSAGES_BUNDLE_NAME = "messages";
    String APP_PATH_REG_EXP = ".*/app/";
    String APP_STRING_DEFAULT_VALUE = "";
    String APP_SUPERUSER_ROLE = "ADMIN";
    String APP_DEFAULT_ROLE = "USER";
    String[] APP_ROLES = new String[]{
            "ADMIN",
            "MANAGER",
            "WORKMAN",
            "USER"};
    String BUNDLE_ACCESS_DENIED_LOGGED_USERS = "access.denied.logged.user";
    String BUNDLE_ACCESS_DENIED_MESSAGE = "access.denied.message";
    String BUNDLE_CURRENCY_STRING = "locale.currency.alpha";
    String BUNDLE_CURRENCY_RATE_INTEGER = "locale.currency.rate";
    String BUNDLE_DEFAULT_IS_CLOSED_MESSAGE = "app.literal.false";
    String BUNDLE_LANGUAGE_FOR_REQUEST = "locale.string";

    String LOG4J_XML_PATH = "src/main/resources/log4j.xml";

    String BUNDLE_REQUEST_STATUS_PREFIX = "app.request.";

    String REQUEST_DEFAULT_STATUS = "REGISTER";
    String UPDATE_REQUEST_MANAGER_VALID_STATUS = "REGISTER";
    String UPDATE_REQUEST_WORKMAN_VALID_STATUS = "ACCEPT";
    String REQUEST_WORKMAN_EDIT_DEFAULT_STATUS = "DONE";
    String REQUEST_MANAGER_EDIT_DEFAULT_STATUS = "ACCEPT";

    Integer MYSQL_DEFAULT_MAX_OPEN_PREPARED_STATEMENTS = 100;
    Integer MYSQL_DEFAULT_MAX_IDLE = 10;
    Integer MYSQL_DEFAULT_MIN_IDLE = 5;
}
