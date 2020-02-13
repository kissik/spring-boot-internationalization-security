package ua.org.workshop.web.form;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestForm {

    private String title;
    private String description;

    @NotNull(message = "{validation.text.error.required.field}")
    @Size(min = 6, max = 50, message = "{validation.text.error.from.six.to.fifty}")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Size(min = 6, max = 255, message = "{validation.text.error.from.six.to.two.five.five}")
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
