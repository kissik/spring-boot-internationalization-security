package ua.org.workshop.web;

import ua.org.workshop.domain.Request;

import javax.validation.constraints.NotNull;

public class StatusForm {
    private String status;
    private Request request;

    @NotNull(message = "This field is required!")
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String toString() {
        return new StringBuilder()
                .append(" status:" + status)
                .toString();
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

}
