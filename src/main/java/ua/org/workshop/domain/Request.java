package ua.org.workshop.domain;

import ua.org.workshop.configuration.ApplicationConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = ApplicationConstants.Request.TABLE_NAME)
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
    @Column(name = ApplicationConstants.Request.COLUMN_ID)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Size(
            min = ApplicationConstants.Varchar.MIN_VARCHAR_6,
            max = ApplicationConstants.Varchar.MAX_VARCHAR_50,
            message = "{validation.text.error.from.six.to.fifty}")
    @Column(name = ApplicationConstants.Request.COLUMN_TITLE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Size(
            min = ApplicationConstants.Varchar.MIN_VARCHAR_6,
            max = ApplicationConstants.Varchar.MAX_VARCHAR_255,
            message = "{validation.text.error.from.six.to.two.five.five}")
    @Column(name = ApplicationConstants.Request.COLUMN_DESCRIPTION)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(
            name = ApplicationConstants.Request.COLUMN_STATUS,
            referencedColumnName = ApplicationConstants.Status.COLUMN_ID)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(
            name = ApplicationConstants.Request.COLUMN_AUTHOR,
            referencedColumnName = ApplicationConstants.Account.COLUMN_ID)
    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(
            name = ApplicationConstants.Request.COLUMN_USER,
            referencedColumnName = ApplicationConstants.Account.COLUMN_ID)
    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    @Column(name = ApplicationConstants.Request.COLUMN_CLOSED)
    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    @Column(name = ApplicationConstants.Request.COLUMN_DATE_CREATED)
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = ApplicationConstants.Request.COLUMN_DATE_UPDATED)
    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Column(name = ApplicationConstants.Request.COLUMN_PRICE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = ApplicationConstants.Request.COLUMN_CAUSE)
    @Size(
            max = ApplicationConstants.Varchar.MAX_VARCHAR_255,
            message = "{validation.text.error.more.than.two.five.five}")
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @Column(name = ApplicationConstants.Request.COLUMN_LANGUAGE)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Transient
    public String getClosedLiteral() {
        return "app.literal." + closed;
    }

    @Override
    public String toString() {
        return " id : " + this.id
                + " title : " + this.title
                + " desc : " + this.description
                + " status: " + this.status
                + " autor: " + this.author
                + " user: " + this.user
                + " cause: " + this.cause
                + " price: " + this.price
                + " language: " + this.language
                + " closed: " + this.closed;
    }
}
