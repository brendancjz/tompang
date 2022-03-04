/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EmptyListException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

/**
 *
 * @author brend
 */
@Named(value = "viewAllUsersManagedBean")
@RequestScoped
public class ViewAllUsersManagedBean {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private List<User> listOfUsers;
    private List<User> filteredUsers;

    @Inject
    private ViewUserDetailsManagedBean viewUserDetailsManagedBean;

    public ViewAllUsersManagedBean() {
    }

    @PostConstruct
    public void retrieveAllUsers() {
        try {
            listOfUsers = userSessionBean.retrieveAllUsers();
        } catch (EmptyListException ex) {
            System.out.println("Unable to retrieve list of users.");
        }
    }

    public void viewUserDetails(ActionEvent event) throws IOException {
        Long userIdToView = (Long) event.getComponent().getAttributes().get("userId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("userIdToView", userIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewUserDetails.xhtml");
    }

    public ViewUserDetailsManagedBean getViewUserDetailsManagedBean() {
        return viewUserDetailsManagedBean;
    }

    public void setViewUserDetailsManagedBean(ViewUserDetailsManagedBean viewUserDetailsManagedBean) {
        this.viewUserDetailsManagedBean = viewUserDetailsManagedBean;
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
