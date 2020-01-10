package ua.org.workshop.domain;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
/**
 * @author kissik
 */
@Entity
@Table(name = "userlist")
@NamedQuery(name = "userlist.byLogin",
        query = "from Account a where a.username = :username")
public class Account {
    public static final Account ACCOUNT = new Account("anonymous");

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String firstNameOrigin;
    private String lastNameOrigin;
    private String lastName;
    private String email;
    private String phone;
    private boolean enabled = true;
    private LocalDate dateCreated;
    private Collection<Role> roles = new HashSet<Role>();

    public Account(){}
    public Account(String username) { this.username = username; }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() { return id; }

    private void setId(Long id) { this.id = id; }

    @NotNull
    @Size(min = 6, max = 50, message = "Can't be less than 6 or more than 50 characters")
    @Column(name = "slogin")
    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    @NotNull
    @Column(name = "spassword")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    @Column(name = "sfirst_name")
    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    @Column(name = "sfirst_name_origin")
    public String getFirstNameOrigin() {
        return firstNameOrigin;
    }

    public void setFirstNameOrigin(String firstNameOrigin) {
        this.firstNameOrigin = firstNameOrigin;
    }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    @Column(name = "slast_name_origin")
    public String getLastNameOrigin() {
        return lastNameOrigin;
    }

    public void setLastNameOrigin(String lastNameOrigin) {
        this.lastNameOrigin = lastNameOrigin;
    }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    @Column(name = "slast_name")
    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    @Transient
    public String getFullName() { return this.firstName + " " + this.lastName;}

    @Transient
    public String getFullNameOrigin() { return this.firstNameOrigin + " " + this.lastNameOrigin;}

    @NotNull
    @Size(min = 6, max = 50, message = "Can't be less than 6 or more than 50 characters")
    @Column(name = "semail")
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @NotNull
    @Pattern(regexp="\\+\\d{12}", message="+380001112233")
    @Column(name = "sphone")
    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    @Column(name = "benabled")
    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    @ManyToMany
    @JoinTable(
            name = "userrole",
            joinColumns = { @JoinColumn(name = "nuser", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "nrole", referencedColumnName = "id") })
    public Collection<Role> getRoles() { return roles; }

    public void setRoles(Collection<Role> roles) { this.roles = roles; }

    @Column(name = "ddate_created")
    public LocalDate getDateCreated() { return dateCreated; }

    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    /* (non-Javadoc)
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     */
    @Transient
    public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.addAll(getRoles());
        return authorities;
    }

    public boolean hasRole(String role){
        for (Role r : getRoles()) {
            if (r.getCode().equals(role)) return true;
        }
        return false;
    }

    public String toString() { return username; }

}
