package ua.org.workshop.web;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.ScriptAssert;

import java.util.Arrays;

/**
 * @author kissik
 */
@ScriptAssert.List({
        @ScriptAssert(
                lang = "javascript",
                script = "_this.confirmPassword.equals(_this.password)",
                message = "account.password.mismatch.message"),
        @ScriptAssert(
                lang = "javascript",
                script = "_this.role.length>0",
                message = "account.role.isnull.message")})
public class AccountForm {
    private String username;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;
    private String firstNameOrigin;
    private String lastNameOrigin;
    private String email;
    private String phone;
    private String[] role = {"ROLE_USER"};

    @NotNull
    @Size(min = 6, max = 50, message = "Can't be less than 6 or more than 50 characters")
    @Pattern(regexp="[a-z_]{1}[0-9a-z_]*", message = "Latin symbols, '_' symbol and digits only")
    public String getUsername() { return username; }

    public void setUsername(String userName) { this.username = userName; }

    @NotNull
    @Size(min = 8, max = 50, message = "Can't be less than 8 or more than 50 characters")
    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getConfirmPassword() { return confirmPassword; }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    @NotNull
    @Size(min = 6, max = 50, message = "Can't be less than 6 or more than 50 characters")
    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    @NotNull
    @Pattern(regexp="\\+\\d{12}", message="+380001112233")
    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    @NotNull(message = "Choose at least one role!")
    public String[] getRole() { return role; }

    public void setRole(String[] role) { this.role = role; }

    public String toString() {
        return new StringBuilder()
                .append(" username: " + username)
                .append(" firstName: " + firstName)
                .append(" lastName: " + lastName)
                .append(" firstNameOrigin: " + firstNameOrigin)
                .append(" lastNameOrigin: " + lastNameOrigin)
                .append(" email: " + email)
                .append(" phone: " + phone)
                .append(" role:" + Arrays.toString(role))
                .toString();
    }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    public String getFirstNameOrigin() {
        return firstNameOrigin;
    }

    public void setFirstNameOrigin(String firstNameOrigin) {
        this.firstNameOrigin = firstNameOrigin;
    }

    @NotNull
    @Size(min = 3, max = 50, message = "Can't be less than 3 or more than 50 characters")
    public String getLastNameOrigin() {
        return lastNameOrigin;
    }

    public void setLastNameOrigin(String lastNameOrigin) {
        this.lastNameOrigin = lastNameOrigin;
    }
}
