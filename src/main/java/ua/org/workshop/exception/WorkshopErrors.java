package ua.org.workshop.exception;

public enum WorkshopErrors {

    DATABASE_CONNECTION_ERROR (100, "error.db.connection"),

    ACCOUNT_NOT_FOUND_ERROR (200, "account.not.found.error"),
    ACCOUNT_CREATE_NEW_ERROR (201, "account.create.error"),
    ACCOUNT_LIST_IS_EMPTY_ERROR (202, "account.list.empty.error"),

    REQUEST_NOT_FOUND_ERROR (300, "request.not.found.error"),
    REQUEST_CREATE_NEW_ERROR (301, "request.create.error"),
    REQUEST_LIST_IS_EMPTY_ERROR (302, "request.list.empty.error");

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
