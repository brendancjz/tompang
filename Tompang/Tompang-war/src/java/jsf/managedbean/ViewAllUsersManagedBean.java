/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EmptyListException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

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
