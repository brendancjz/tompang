/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

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
    @Column(nullable = false, length = 30)
    @NotNull
    private String firstName;
    @Column(nullable = false, length = 30)
    @NotNull
    private String lastName;
    @Column(nullable = false, unique = true, length = 50)
    @Email
    @NotNull
    private String email;
    @Column(nullable = false, unique = true, length = 30)
    @NotNull
    private String username;
    @Column(nullable = false, length = 30)
    @NotNull
    @Size(min=5, max=30)
    private String password;
    @Column(nullable = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfBirth;
    @Column(nullable = false, unique = true, length = 8)
    @NotNull
    @Digits(integer=8, fraction=0)
    @Positive
    private Long contactNumber;
    @Column(nullable = false)
    @NotNull
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date joinedOn;
    @Column(nullable = false)
    @NotNull
    private Boolean isAdmin;
    @Column(nullable = false)
    @NotNull
    private Boolean isDisabled;
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String salt;
    @OneToMany
    private List<CreditCard> creditCards;
    @OneToMany(mappedBy = "createdBy")
    private List<Conversation> conversations;
    @OneToMany(mappedBy = "createdBy")
    private List<Listing> createdListings;
    @OneToMany(mappedBy = "buyer")
    private List<Transaction> buyerTransactions;
    @OneToMany(mappedBy = "seller")
    private List<Transaction> sellerTransactions;
    @OneToMany
    private List<User> following;
    @OneToMany
    private List<Listing> likedListings;
    

    public User() {
        this.creditCards = new ArrayList<>();
        this.conversations = new ArrayList<>();
        this.createdListings = new ArrayList<>();
        this.buyerTransactions = new ArrayList<>();
        this.sellerTransactions = new ArrayList<>();
        this.following = new ArrayList<>();
        this.likedListings = new ArrayList<>();
        this.joinedOn = Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        this.isDisabled = false;
        this.salt = "";
    }

    public User(String firstName, String lastName, String email, String username, String password, Date dateOfBirth, Long contactNumber, Boolean isAdmin) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.contactNumber = contactNumber;
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
    
    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public List<Listing> getCreatedListings() {
        return createdListings;
    }

    public List<Transaction> getBuyerTransactions() {
        return buyerTransactions;
    }

    public List<Transaction> getSellerTransactions() {
        return sellerTransactions;
    }
    
    public List<User> getFollowing() {
        return following;
    }
    
    public List<Listing> getLikedListings() {
        return likedListings;
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
    
    public void setIsDisabled(Boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public void setCreatedListings(List<Listing> createdListings) {
        this.createdListings = createdListings;
    }

    public void setBuyerTransactions(List<Transaction> buyerTransactions) {
        this.buyerTransactions = buyerTransactions;
    }

    public void setSellerTransactions(List<Transaction> sellerTransactions) {
        this.sellerTransactions = sellerTransactions;
    }
    
    public void setFollowing(List<User> following) {
        this.following = following;
    }
    
    public void setLikedListings(List<Listing> likedListings) {
        this.likedListings = likedListings;
    }

    /**
     * @return the salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt the salt to set
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }
}
