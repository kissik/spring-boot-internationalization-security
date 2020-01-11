package ua.org.workshop.web;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestForm {

    private String title;
    private String description;

    @NotNull
    @Size(min = 6, max = 50, message = "Can't be less than 6 or more than 50 characters")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Size(min = 6, max = 255, message = "Can't be less than 6 or more than 255 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return new StringBuilder()
                .append(" title:" + title)
                .append(" description:" + description)
                .toString();
    }
}
