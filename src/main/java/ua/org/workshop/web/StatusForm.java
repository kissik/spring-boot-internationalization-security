package ua.org.workshop.web;

import ua.org.workshop.domain.Request;
import ua.org.workshop.exception.WorkshopErrors;
import ua.org.workshop.exception.WorkshopException;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Optional;

public class StatusForm {
    private String status;
    private BigDecimal price;
    private String cause;
    private Request request;

    @NotNull(message = "This field is required!")
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String toString() {
        return new StringBuilder()
                .append(" status:" + status)
                .append(" price:" + getPrice().toString())
                .append(" cause:" + getCause())
                .toString();
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public BigDecimal getPrice() throws WorkshopException {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
