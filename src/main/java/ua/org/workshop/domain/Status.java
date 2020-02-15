package ua.org.workshop.domain;

import ua.org.workshop.configuration.ApplicationConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author kissik
 */
@Entity
@Table(name = ApplicationConstants.Status.TABLE_NAME)
public class Status {
    private Long id;
    private String code;
    private String name;
    private boolean closed;
    private Collection<Status> nextStatuses = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ApplicationConstants.Status.COLUMN_ID)
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = ApplicationConstants.Status.COLUMN_CODE)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = ApplicationConstants.Status.COLUMN_NAME)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = ApplicationConstants.Status.COLUMN_CLOSED)
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @ManyToMany
    @JoinTable(
            name = ApplicationConstants.NextStatuses.TABLE_NAME,
            joinColumns = {
                    @JoinColumn(
                            name = ApplicationConstants.NextStatuses.COLUMN_STATUS,
                            referencedColumnName = ApplicationConstants.Status.COLUMN_ID)},
            inverseJoinColumns = {
                    @JoinColumn(
                            name = ApplicationConstants.NextStatuses.COLUMN_NEXT_STATUS,
                            referencedColumnName = ApplicationConstants.Status.COLUMN_ID)})
    public Collection<Status> getNextStatuses() {
        return nextStatuses;
    }

    public void setNextStatuses(Collection<Status> nextStatuses) {
        this.nextStatuses = nextStatuses;
    }

    public int hashCode() {
        return getCode().hashCode();
    }

    public String toString() {
        return this.code + " " + this.name + ": " + this.nextStatuses.toArray().toString();
    }
}
