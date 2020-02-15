package ua.org.workshop.domain;

import org.springframework.security.core.GrantedAuthority;
import ua.org.workshop.configuration.ApplicationConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author kissik
 */
@Entity
@Table(name = ApplicationConstants.Role.TABLE_NAME)
public class Role implements GrantedAuthority {
    private Long id;
    private String code;
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ApplicationConstants.Role.COLUMN_ID)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = ApplicationConstants.Role.COLUMN_CODE)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = ApplicationConstants.Role.COLUMN_NAME)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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