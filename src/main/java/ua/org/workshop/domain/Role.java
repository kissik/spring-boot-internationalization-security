package ua.org.workshop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author kissik
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "role")
public class Role implements GrantedAuthority{
    private Long id;
    private String code;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() { return id; }

    @SuppressWarnings("unused")
    public void setId(Long id) { this.id = id; }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = "scode")
    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = "sname")
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    /* (non-Javadoc)
     * @see org.springframework.security.core.GrantedAuthority#getAuthority()
     */
    @Override
    @Transient
    public String getAuthority() {
        return code;
    }

    @Override
    public boolean equals(Object o) {
        GrantedAuthority ga = (GrantedAuthority) o;
        return (getAuthority().equals(ga.getAuthority()));
    }

    @Override
    public int hashCode() {
        return getAuthority().hashCode();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}