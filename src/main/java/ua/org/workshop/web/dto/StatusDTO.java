package ua.org.workshop.web.dto;

import ua.org.workshop.domain.Status;

import java.util.List;

/**
 * @author kissik
 */
public class StatusDTO {
    private String code;
    private String name;
    private String value;
    private List nextStatuses;

    public StatusDTO(Status status) {
        this.code = status.getCode();
        this.name = status.getName();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List getNextStatuses() {
        return nextStatuses;
    }

    public void setNextStatuses(List nextStatuses) {
        this.nextStatuses = nextStatuses;
    }

    @Override
    public String toString() {
        return this.code + " " + this.name + ": " + this.nextStatuses.toString();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
