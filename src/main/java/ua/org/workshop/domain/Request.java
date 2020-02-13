package ua.org.workshop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "request_list")
public class Request {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Account author;
    private Account user;
    private boolean closed = false;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private BigDecimal price;
    private String cause;
    private String language;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Size(min = 6, max = 50, message = "{validation.text.error.from.six.to.fifty}")
    @Column(name = "stitle")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Size(min = 6, max = 255, message = "{validation.text.error.from.six.to.two.five.five}")
    @Column(name = "sdescription")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(name="nstatus", referencedColumnName = "id")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(name="nuser", referencedColumnName = "id")
    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    @Column(name = "ddate_created")
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = "ddate_updated")
    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Column(name = "nprice")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "scause")
    @Size(max = 255, message = "{validation.text.error.more.than.two.five.five}")
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String toString(){
        return this.title + ": " + this.description;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = "slang")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(name="nauthor", referencedColumnName = "id")
    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    @Column(name = "bclosed")
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Transient
    public String getClosedLiteral(){
        return "app.literal." + String.valueOf(closed);
    }
}
