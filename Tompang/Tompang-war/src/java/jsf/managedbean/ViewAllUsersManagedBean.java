/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EmptyListException;
import java.util.ArrayList;
import java.util.List;
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
    
    
    public ViewAllUsersManagedBean() {
//        listOfUsers = new ArrayList<>();
//        
//        try {
//            listOfUsers = userSessionBean.retrieveAllUsers();
//
//            System.out.println(listOfUsers.size());
//            for (User user : listOfUsers) {
//                System.out.println(user.getUserId());
//            }
//        } catch (EmptyListException ex) {
//            System.out.println("Unable to retrieve list of users.");
//        }
    }

    public List<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(List<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }
}
