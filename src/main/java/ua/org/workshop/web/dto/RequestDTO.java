package ua.org.workshop.web.dto;

import ua.org.workshop.configuration.ApplicationConstants;
import ua.org.workshop.domain.Request;

/**
 * @author kissik
 */
public class RequestDTO {
    private Long id;
    private String title;
    private String description;
    private StatusDTO status;
    private AccountDTO author;
    private AccountDTO user;
    private String closed = ApplicationConstants.BUNDLE_DEFAULT_IS_CLOSED_MESSAGE;
    private String dateCreated;
    private String dateUpdated;
    private String price;
    private String cause;
    private String language;

    public RequestDTO(Request request) {
        this.id = request.getId();
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.language = request.getLanguage();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusDTO getStatus() {
        return status;
    }

    public void setStatus(StatusDTO status) {
        this.status = status;
    }

    public AccountDTO getUser() {
        return user;
    }

    public void setUser(AccountDTO user) {
        this.user = user;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String toString() {
        return this.title + ": " + this.description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public AccountDTO getAuthor() {
        return author;
    }

    public void setAuthorDTO(AccountDTO author) {
        this.author = author;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
