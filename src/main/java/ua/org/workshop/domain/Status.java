package ua.org.workshop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author kissik
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "status")
public class Status {
    private Long id;
    private String code;
    private String name;
    private boolean close;
    private Collection<Status> nextStatuses = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    private void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = "scode")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = "sname")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = "bclose")
    public boolean isClose() {
        return close;
    }

    public void setClose(boolean close) {
        this.close = close;
    }

    @ManyToMany
    @JoinTable(
            name = "next_statuses",
            joinColumns = {@JoinColumn(name = "nstatus", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "nnextstatus", referencedColumnName = "id")})
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
