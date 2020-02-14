package ua.org.workshop.web.form;

import javax.validation.constraints.NotNull;

public class WorkmanUpdateRequestForm {
    private String status;

    @NotNull(message = "{validation.text.error.required.field}")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(" status : " + getStatus())
                .toString();
    }
}
