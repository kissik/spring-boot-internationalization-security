package ua.org.workshop.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * TODO send all validation messages to messages properties
 */
@Entity
@Table(name = "request_list_history")
public class RequestHistory {
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(min = 6, max = 50, message = "Can't be less than 6 or more than 50 characters")
    @Column(name = "stitle")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @Size(min = 1, max = 255, message = "Can't be less than 6 or more than 255 characters")
    @Column(name = "sdescription")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name="nstatus", referencedColumnName = "id")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @NotNull
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
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String toString(){
        return this.title + ": " + this.description;
    }

    @NotNull
    @Column(name = "slang")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @NotNull
    @ManyToOne
    @JoinColumn(name="nauthor", referencedColumnName = "id")
    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    @Column(name = "sreview")
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    @Column(name = "nrating")
    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }
}
