package ua.org.workshop.domain;

import ua.org.workshop.configuration.ApplicationConstants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = ApplicationConstants.HistoryRequest.TABLE_NAME)
public class HistoryRequest {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private Account author;
    private Account user;
    private LocalDate dateCreated;
    private LocalDate dateUpdated;
    private BigDecimal price;
    private String cause;
    private String language;
    private String review;
    private Long rating;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_ID)
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
    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_TITLE)
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
    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_DESCRIPTION)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(
            name = ApplicationConstants.HistoryRequest.COLUMN_STATUS,
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
            name = ApplicationConstants.HistoryRequest.COLUMN_USER,
            referencedColumnName = ApplicationConstants.Account.COLUMN_ID)
    public Account getUser() {
        return user;
    }

    public void setUser(Account user) {
        this.user = user;
    }

    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_DATE_CREATED)
    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_DATE_UPDATED)
    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_PRICE)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_CAUSE)
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
    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_LANGUAGE)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @NotNull(message = "{validation.text.error.required.field}")
    @ManyToOne
    @JoinColumn(
            name = ApplicationConstants.HistoryRequest.COLUMN_AUTHOR,
            referencedColumnName = ApplicationConstants.Account.COLUMN_ID)
    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_REVIEW)
    @Size(
            max = ApplicationConstants.Varchar.MAX_VARCHAR_255,
            message = "{validation.text.error.more.than.two.five.five}")
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Column(name = ApplicationConstants.HistoryRequest.COLUMN_RATING)
    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return " title : " + this.title
                + " description : " + this.description
                + " status : " + this.status
                + " author : " + this.author
                + " user : " + this.user;
    }
}
