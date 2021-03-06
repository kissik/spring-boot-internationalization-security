package ua.org.workshop.web.form;

import ua.org.workshop.configuration.ApplicationConstants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class ManagerUpdateRequestForm {
    private String status;
    private BigDecimal price;
    private String cause;

    @NotNull(message = "{validation.text.error.required.field}")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Size(
            max = ApplicationConstants.Varchar.MAX_VARCHAR_255,
            message = "{validation.text.error.from.six.to.two.five.five}")
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Override
    public String toString() {
        return " status : " + status +
                " price : " + price +
                " cause : " + cause;
    }
}
