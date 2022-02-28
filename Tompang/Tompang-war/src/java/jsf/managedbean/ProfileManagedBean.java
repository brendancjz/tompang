/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EntityNotFoundException;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Past;

/**
 *
 * @author brend
 */
@Named(value = "profileManagedBean")
@ViewScoped
public class ProfileManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private User user;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Long contactNum;
    @Past
    private Date dob;
    private Date joinedOn;
    private Boolean toggleEditProfile;
    private Boolean toggleChangePassword;

    private String currPassword;
    private String newPassword;

    public ProfileManagedBean() {
        initialise();
    }

    private void initialise() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        username = user.getUsername();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        contactNum = user.getContactNumber();
        dob = user.getDateOfBirth();
        joinedOn = user.getJoinedOn();
        toggleEditProfile = true;
        toggleChangePassword = false;
    }

    public void update() {
        try {
            userSessionBean.updateUserDetails(user.getUserId(), firstName, lastName, email, username, dob, contactNum);
            User updatedUser = userSessionBean.getUserByUserId(user.getUserId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", updatedUser);

            initialise();

        } catch (EntityNotFoundException ex) {
            System.out.println("Unable to update User Details");
        }
    }

    public void changePassword() {
        System.out.println("Change Password.");
        try {
            if (user.getPassword().equals(currPassword)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User successfully changed password.", null));
                userSessionBean.updateUserPassword(user.getUserId(), newPassword);
                
                User updatedUser = userSessionBean.getUserByUserId(user.getUserId());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", updatedUser);
                user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Passwords do not match.", null));

            }

        } catch (EntityNotFoundException ex) {
            System.out.println("Unable to update User Password");
        }
    }

    public void toggleEditProfile() {
        this.toggleEditProfile = true;
        this.toggleChangePassword = false;
    }

    public void toggleChangePassword() {
        this.toggleChangePassword = true;
        this.toggleEditProfile = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getContactNum() {
        return contactNum;
    }

    public void setContactNum(Long contactNum) {
        this.contactNum = contactNum;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getJoinedOn() {
        return joinedOn;
    }

    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    public Boolean getToggleEditProfile() {
        return toggleEditProfile;
    }

    public void setToggleEditProfile(Boolean toggleEditProfile) {
        this.toggleEditProfile = toggleEditProfile;
    }

    public Boolean getToggleChangePassword() {
        return toggleChangePassword;
    }

    public void setToggleChangePassword(Boolean toggleChangePassword) {
        this.toggleChangePassword = toggleChangePassword;
    }

    public String getCurrPassword() {
        return currPassword;
    }

    public void setCurrPassword(String currPassword) {
        this.currPassword = currPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
