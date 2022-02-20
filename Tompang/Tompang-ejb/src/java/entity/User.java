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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author brend
 */
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirth;
    private Long contactNumber;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date joinedOn;
    private Boolean isAdmin;
    @OneToMany
    private List<CreditCard> creditCards;
    @OneToMany(mappedBy = "createdBy")
    private List<Conversation> conversations;
    @OneToMany(mappedBy = "createdBy")
    private List<Listing> listings;
    @OneToMany(mappedBy = "buyer")
    private List<Transaction> buyerTransactions;
    @OneToMany(mappedBy = "seller")
    private List<Transaction> sellerTransactions;

    public User() {
        this.creditCards = new ArrayList<>();
        this.conversations = new ArrayList<>();
        this.listings = new ArrayList<>();
        this.buyerTransactions = new ArrayList<>();
        this.sellerTransactions = new ArrayList<>();
        
    }

    public User(String firstName, String lastName, String email, String username, String password, Date dateOfBirth, Long contactNumber, Date joinedOn, Boolean isAdmin) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
        this.joinedOn = joinedOn;
        this.isAdmin = isAdmin;
    }

    public Long getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public List<Transaction> getBuyerTransactions() {
        return buyerTransactions;
    }

    public List<Transaction> getSellerTransactions() {
        return sellerTransactions;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }

    public void setBuyerTransactions(List<Transaction> buyerTransactions) {
        this.buyerTransactions = buyerTransactions;
    }

    public void setSellerTransactions(List<Transaction> sellerTransactions) {
        this.sellerTransactions = sellerTransactions;
    }
    
    
    
    
    
    
}
