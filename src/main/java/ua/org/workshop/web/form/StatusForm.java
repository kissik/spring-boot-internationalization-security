package ua.org.workshop.web.form;

import ua.org.workshop.domain.Request;
import ua.org.workshop.exception.WorkshopException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class StatusForm {
    private String status;
    private BigDecimal price;
    private String cause;
    private Request request;

    @NotNull(message = "{validation.text.error.required.field}")
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String toString() {
        return new StringBuilder()
                .append(" status:" + getStatus())
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

    @Size(max = 255, message = "{validation.text.error.from.six.to.two.five.five}")
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
