/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EntityNotFoundException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author GuoJun
 */
@Named(value = "userManagedBean")
@RequestScoped
public class UserManagedBean implements Serializable {

    @EJB
    private UserSessionBeanLocal userSessionBean;

    private User user;
    private List<User> following;
    private List<User> followers;
    
    public UserManagedBean() {
    }
    
    @PostConstruct
    public void retrieveUser() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
        
        following = user.getFollowing();
        followers = user.getFollowers();
        
        
    }
    
    public void followUser(AjaxBehaviorEvent event) {
        try {
            User userToFollow = (User) event.getComponent().getAttributes().get("user");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            userSessionBean.follow(user.getUserId(), userToFollow.getUserId());
            
            //Update user to view in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveUser(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void unfollowUser(AjaxBehaviorEvent event) {
        try {
            User userToUnfollow = (User) event.getComponent().getAttributes().get("user");
            User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
            
            userSessionBean.unfollow(user.getUserId(), userToUnfollow.getUserId());
            
            //Update user to view in session scope
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", userSessionBean.getUserByUserId(user.getUserId()));
            this.retrieveUser(); 
        } catch (EntityNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }
    
}
