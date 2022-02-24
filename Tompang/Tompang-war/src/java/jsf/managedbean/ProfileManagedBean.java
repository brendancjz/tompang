/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.User;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;

/**
 *
 * @author brend
 */
@Named(value = "profileManagedBean")
@Dependent
public class ProfileManagedBean {

    private User user;
    public ProfileManagedBean() {
        user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUser");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
}
