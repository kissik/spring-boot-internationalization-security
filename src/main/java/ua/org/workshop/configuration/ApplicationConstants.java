package ua.org.workshop.configuration;

import java.math.BigDecimal;

public interface ApplicationConstants {

    interface InitParameters {
        String APP_ANONYMOUS_ACCOUNT_USERNAME = "anonymous";
        String APP_ENCODING = "UTF-8";
        int APP_BCRYPT_SALT = 11;
        String APP_REQUEST_ENCODING_PARAMETER = "requestEncoding";
    }

    interface Varchar {
        int MAX_VARCHAR_255 = 255;
        int MAX_VARCHAR_50 = 50;
        int MIN_VARCHAR_3 = 3;
        int MIN_VARCHAR_6 = 6;
        int MIN_VARCHAR_8 = 8;
    }

    interface Account {
        interface Sort {
            String USERNAME = "username";
            String DATE_CREATED = "dateCreated";
        }

        String COLUMN_ID = "id";
        String COLUMN_USERNAME = "slogin";
        String COLUMN_PASSWORD = "spassword";
        String COLUMN_FIRST_NAME = "sfirst_name";
        String COLUMN_FIRST_NAME_ORIGIN = "sfirst_name_origin";
        String COLUMN_LAST_NAME_ORIGIN = "slast_name_origin";
        String COLUMN_LAST_NAME = "slast_name";
        String COLUMN_EMAIL = "semail";
        String COLUMN_PHONE = "sphone";
        String COLUMN_ENABLED = "benabled";
        String COLUMN_DATE_CREATED = "ddate_created";
        String TABLE_NAME = "user_list";
    }

    interface UserRole {
        String COLUMN_USER_ID = "nuser";
        String COLUMN_ROLE_ID = "nrole";
        String TABLE_NAME = "user_role";
    }

    interface Role {
        String COLUMN_ID = "id";
        String COLUMN_CODE = "scode";
        String COLUMN_NAME = "sname";
        String TABLE_NAME = "role";
    }

    interface Status {
        String COLUMN_ID = "id";
        String COLUMN_CODE = "scode";
        String COLUMN_NAME = "sname";
        String COLUMN_CLOSED = "bclosed";
        String TABLE_NAME = "status";
    }

    interface NextStatuses {
        String TABLE_NAME = "next_statuses";
        String COLUMN_STATUS = "nstatus";
        String COLUMN_NEXT_STATUS = "nnext_status";
    }

    interface Request {
        interface Sort {
            String TITLE = "title";
            String DATE_CREATED = "dateCreated";
        }

        String COLUMN_ID = "id";
        String COLUMN_TITLE = "stitle";
        String COLUMN_DESCRIPTION = "sdescription";
        String COLUMN_STATUS = "nstatus";
        String COLUMN_AUTHOR = "nauthor";
        String COLUMN_USER = "nuser";
        String COLUMN_CLOSED = "bclosed";
        String COLUMN_DATE_CREATED = "ddate_created";
        String COLUMN_DATE_UPDATED = "ddate_updated";
        String COLUMN_PRICE = "nprice";
        String COLUMN_CAUSE = "scause";
        String COLUMN_LANGUAGE = "slanguage";
        String TABLE_NAME = "request_list";
    }

    interface HistoryRequest {
        interface Sort {
            String TITLE = "title";
            String DATE_CREATED = "dateCreated";
        }

        String COLUMN_ID = "id";
        String COLUMN_TITLE = "stitle";
        String COLUMN_DESCRIPTION = "sdescription";
        String COLUMN_STATUS = "nstatus";
        String COLUMN_AUTHOR = "nauthor";
        String COLUMN_USER = "nuser";
        String COLUMN_CLOSED = "bclosed";
        String COLUMN_DATE_CREATED = "ddate_created";
        String COLUMN_DATE_UPDATED = "ddate_updated";
        String COLUMN_PRICE = "nprice";
        String COLUMN_CAUSE = "scause";
        String COLUMN_LANGUAGE = "slanguage";
        String COLUMN_REVIEW = "sreview";
        String COLUMN_RATING = "nrating";
        String TABLE_NAME = "history_request_list";
    }

    interface Bean {
        String APP_MESSAGE_BUNDLE_BEAN_NAME = "messageSource";
    }

    interface Page {
        int PAGE_DEFAULT_VALUE = 0;
        int SIZE_DEFAULT_VALUE = 5;
    }

    interface PathVariable {
        String ID = "id";
    }

    interface ModelAttribute {
        String EXCEPTION = "exception";
        String ERROR_MESSAGE = "message";
        String TITLE = "title";
        String DEFAULT_ERROR_BUNDLE_MESSAGE = "error.contact.admin";

        interface Form {
            String ACCOUNT_FORM = "account";
            String ROLE_FORM = "role";
            String MANAGER_UPDATE_REQUEST_FORM = "requestForm";
            String WORKMAN_UPDATE_REQUEST_FORM = "requestForm";
            String REQUEST_FORM = "request";
            String LOGIN_USERNAME = "username";
        }

        interface View {
            String ACCOUNT = "account";
            String REQUEST = "request";
            String ROLES_LIST = "rolesList";
        }

    }

    interface HttpRequestParameter {
        String APP_LANG_PARAMETER = "lang";
        String HISTORY_REQUEST_FEEDBACK_PARAMETER = "feedback";
        String HISTORY_REQUEST_RATING_PARAMETER = "rating";
    }

    String APP_DEFAULT_LANGUAGE = "en";
    Long APP_DEFAULT_ID = -1L;
    BigDecimal APP_DEFAULT_PRICE = BigDecimal.ZERO;
    BigDecimal APP_DEFAULT_RATING_VALUE = BigDecimal.ONE;

    String APP_MESSAGES_BUNDLE_NAME = "messages";
    String APP_STRING_DEFAULT_VALUE = "";
    String APP_SUPERUSER_ROLE = "ADMIN";
    String APP_DEFAULT_ROLE = "USER";

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
}
