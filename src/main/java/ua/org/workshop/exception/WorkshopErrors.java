package ua.org.workshop.exception;

public enum WorkshopErrors {

    PRICE_NOT_FOUND_ERROR(10, "price.not.found.error"),
    CAUSE_NOT_FOUND_ERROR(15, "cause.not.found.error"),
    COMMENT_NOT_FOUND_ERROR(20, "comment.not.found.error"),
    RATING_NOT_FOUND_ERROR(25, "rating.not.found.error"),

    DATABASE_CONNECTION_ERROR (100, "error.db.connection"),

    ACCOUNT_NOT_FOUND_ERROR (200, "account.not.found.error"),
    ACCOUNT_CREATE_NEW_ERROR (201, "account.create.error"),
    ACCOUNT_LIST_IS_EMPTY_ERROR (202, "account.list.empty.error"),

    ROLE_NOT_FOUND_ERROR(250, "role.not.found.error"),

    REQUEST_NOT_FOUND_ERROR (300, "request.not.found.error"),
    REQUEST_CREATE_NEW_ERROR (301, "request.create.error"),
    REQUEST_LIST_IS_EMPTY_ERROR (302, "request.list.empty.error"),

    REQUEST_HISTORY_NOT_FOUND_ERROR(330, "request.history.not.found.error"),
    REQUEST_LIST_HISTORY_IS_EMPTY_ERROR (331, "request.history.list.not.found.error"),

    STATUS_NOT_FOUND_ERROR(350, "status.not.found.error");

    private int code;
    private String message;

    WorkshopErrors(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return code;
    }

    public String message(){
        return message;
    }
}
