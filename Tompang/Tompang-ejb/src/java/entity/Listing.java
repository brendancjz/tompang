/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 *
 * @author brend
 */
@Entity
public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long listingId;
    @Column(nullable = false, length = 50)
    @NotNull
    private String country;
    @Column(nullable = false, length = 50)
    @NotNull
    private String city;
    @Column(nullable = false, length = 50)
    @NotNull
    private String title;
    @Column(nullable = false, length = 255)
    @NotNull
    private String description;
    @Column(nullable = false)
    @NotNull
    @Digits(integer=6, fraction=2)
    @PositiveOrZero
    private Double price;
    @Column(nullable = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdOn;
    @Column(nullable = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    @Future
    private Date expectedArrivalDate;
    @Column(nullable = false)
    @NotNull
    @PositiveOrZero
    private Integer numOfLikes;
    @Column(nullable = false)
    @NotNull
    private Boolean isOpen;
    
    @ManyToOne
    private User createdBy;
    @OneToMany(mappedBy = "listing")
    private List<Conversation> conversations;
    @OneToMany
    private List<Photo> photos;

    public Listing() {
        this.conversations = new ArrayList<>();
        this.photos = new ArrayList<>();
    }

    public Listing(String country, String city, String title, String description, Double price, Date createdOn, Date expectedArrivalDate, Integer numOfLikes, Boolean isOpen, User createdBy) {
        this();
        this.country = country;
        this.city = city;
        this.title = title;
        this.description = description;
        this.price = price;
        this.createdOn = createdOn;
        this.expectedArrivalDate = expectedArrivalDate;
        this.numOfLikes = numOfLikes;
        this.isOpen = isOpen;
        this.createdBy = createdBy;
    }

    public Long getListingId() {
        return listingId;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public Date getExpectedArrivalDate() {
        return expectedArrivalDate;
    }

    public Integer getNumOfLikes() {
        return numOfLikes;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setListingId(Long listingId) {
        this.listingId = listingId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public void setExpectedArrivalDate(Date expectedArrivalDate) {
        this.expectedArrivalDate = expectedArrivalDate;
    }

    public void setNumOfLikes(Integer numOfLikes) {
        this.numOfLikes = numOfLikes;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    
    
    
}
