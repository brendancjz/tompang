/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Past;
import org.primefaces.event.FileUploadEvent;

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
    private String profilePic;
    private Date joinedOn;
    private String profileContent;

    private String currPassword;
    private String newPassword;

    private List<User> following;
    private List<User> followers;
    
    private Boolean showUploadedFile; //To hide the initial display of uploaded file

    public ProfileManagedBean() {
        System.out.println("ProfileManagedBean");
        initialise();
    }

    private void initialise() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        username = user.getUsername();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        contactNum = user.getContactNumber();
        setDob(user.getDateOfBirth());
        setProfilePic(user.getProfilePic());
        setJoinedOn(user.getJoinedOn());
        profileContent = "EDIT_PROFILE";
        setFollowing(user.getFollowing());
        setFollowers(user.getFollowers());
    }

    public void update() {
        try {
            userSessionBean.updateUserDetails(user.getUserId(), firstName, lastName, email, username, dob, getProfilePic(), contactNum);
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

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1")
                    + System.getProperty("file.separator") + event.getFile().getFileName();

            System.err.println("********** Profile Managed Bean.handleFileUpload(): File name: " + event.getFile().getFileName());
            System.err.println("********** Profile Managed Bean.handleFileUpload(): newFilePath: " + newFilePath);

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputStream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();

            profilePic = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("uploadedFilesPath") + "/" + event.getFile().getFileName();
            showUploadedFile = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void toggleEditProfile() {
        profileContent = "EDIT_PROFILE";
    }

    public void toggleChangePassword() {
        profileContent = "CHANGE_PASSWORD";
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

    public String getProfileContent() {
        return profileContent;
    }

    public void setProfileContent(String profileContent) {
        this.profileContent = profileContent;
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

    /**
     * @return the profilePic
     */
    public String getProfilePic() {
        return profilePic;
    }

    /**
     * @param profilePic the profilePic to set
     */
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    /**
     * @param joinedOn the joinedOn to set
     */
    public void setJoinedOn(Date joinedOn) {
        this.joinedOn = joinedOn;
    }

    /**
     * @return the following
     */
    public List<User> getFollowing() {
        return following;
    }

    /**
     * @param following the following to set
     */
    public void setFollowing(List<User> following) {
        this.following = following;
    }

    /**
     * @return the followers
     */
    public List<User> getFollowers() {
        return followers;
    }

    /**
     * @param followers the followers to set
     */
    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public Boolean getShowUploadedFile() {
        return showUploadedFile;
    }

    public void setShowUploadedFile(Boolean showUploadedFile) {
        this.showUploadedFile = showUploadedFile;
    }

    
}
