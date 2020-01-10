package ua.org.workshop.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author kissik
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "status")
@NamedQuery(name = "status.byCode", query = "from Status where code= :code")
public class Status {
    private Long id;
    private String code;
    private String name;
    private Collection<Status> nextStatuses = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() { return id; }

    @SuppressWarnings("unused")
    private void setId(Long id) { this.id = id; }

    @Column(name = "scode")
    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    @Column(name = "sname")
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @ManyToMany
    @JoinTable(
            name = "nextstatuses",
            joinColumns = { @JoinColumn(name = "nstatus", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "nnextstatus", referencedColumnName = "id") })
    public Collection<Status> getNextStatuses() { return nextStatuses; }

    public void setNextStatuses(Collection<Status> nextStatuses) { this.nextStatuses = nextStatuses; }

    public int hashCode() {
        return getCode().hashCode();
    }

    public String toString(){
        return this.code + " " + this.name + ": " + this.nextStatuses.toArray().toString();
    }
}
