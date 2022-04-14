/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

import javax.inject.Inject;

/**
 *
 * @author brend
 */
@Named(value = "viewAllUsersManagedBean")
@ViewScoped
public class ViewAllUsersManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private List<User> listOfUsers;
    private List<User> filteredUsers;

    @Inject
    private ViewUserDetailsManagedBean viewUserDetailsManagedBean;

    private User userToUpdate;

    public ViewAllUsersManagedBean() {
    }

    @PostConstruct
    public void retrieveAllUsers() {
        System.out.println("******* ViewAllUsersManagedBean.retrieveAllUsers()");
        try {
            listOfUsers = userSessionBean.retrieveAllUsers();
        } catch (EmptyListException ex) {
            System.out.println("Unable to retrieve list of users.");
        }
    }

    public void viewUserDetails(ActionEvent event) throws IOException {
        System.out.println("*** ViewAllUsersManagedBean.viewUserDetails()");
        Long userIdToView = (Long) event.getComponent().getAttributes().get("userId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userIdToView", userIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewUserDetails.xhtml");
    }

    public void deleteUser(ActionEvent event) {
        System.out.println("*** ViewAllUsersManagedBean.deleteUser()");
        try {
            User userEntityToDelete = (User) event.getComponent().getAttributes().get("userEntityToDelete");
            userSessionBean.deleteUser(userEntityToDelete.getUserId());

            try {
                User user = userSessionBean.getUserByUserId(userEntityToDelete.getUserId());
                //Means user is disabled, not deleted.
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User disabled successfully", null));
                this.retrieveAllUsers();

            } catch (EntityNotFoundException ex) {
                listOfUsers.remove(userEntityToDelete);

                if (filteredUsers != null) {
                    filteredUsers.remove(userEntityToDelete);
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User deleted successfully", null));
            }
        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting user: " + ex.getMessage(), null));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public ViewUserDetailsManagedBean getViewUserDetailsManagedBean() {
        return viewUserDetailsManagedBean;
    }

    public void setViewUserDetailsManagedBean(ViewUserDetailsManagedBean viewUserDetailsManagedBean) {
        this.viewUserDetailsManagedBean = viewUserDetailsManagedBean;
    }

    public void updateUser(ActionEvent event) {
        System.out.println("*** ViewAllUsersManagedBean.updateUser()");
        User user = (User) event.getComponent().getAttributes().get("user");
        this.userToUpdate = user;
    }

    public void saveUser(ActionEvent event) {
        System.out.println("*** ViewAllUsersManagedBean.saveUser()");
        try {
            userSessionBean.updateUserDetails(userToUpdate);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User updated successfully", null));
            
        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Failed to update User", null));
        }
    }

    public String getUserDOB(User user) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(user.getDateOfBirth());
    }

    public String getUserJoinedOn(User user) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(user.getJoinedOn());
    }

    public Integer getNumberOfUsers() {
        try {
            return userSessionBean.retrieveAllUsers().size();
        } catch (EmptyListException ex) {
            System.out.println(ex.getMessage());
        }

        return 0;
    }

    public User getUserToUpdate() {
        return userToUpdate;
    }

    public void setUserToUpdate(User userToUpdate) {
        this.userToUpdate = userToUpdate;
    }

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
}
