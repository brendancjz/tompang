/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.EntityNotFoundException;
import java.io.IOException;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;

/**
 *
 * @author brend
 */
@Named(value = "loginManagedBean")
@RequestScoped
public class LoginManagedBean {

    @EJB
    private UserSessionBeanLocal userSessionBean;
    private String username;
    private String password;

    public LoginManagedBean() {
    }

    public void redirectToLoginPage(ActionEvent event) throws IOException {
        System.out.println("*** LoginManagedBean.redirectToLoginPage()");
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.xhtml");
    }

    public void redirectToHomePage(ActionEvent event) throws IOException {
        System.out.println("*** LoginManagedBean.redirectToHomePage()");
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/home.xhtml");
    }
    
    public void redirectToProfilePage(ActionEvent event) throws IOException {
        System.out.println("*** LoginManagedBean.redirectToProfilePage()");
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/profile.xhtml");
    }
    
    public void login(ActionEvent event) throws IOException {
        System.out.println("*** LoginManagedBean.login()");
        try {
            User user = (User) userSessionBean.retrieveUserByUsername(username);

            if (user.getPassword().equals(password)) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", user);
                FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/home.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Incorrect username or password.", null));
            }

        } catch (EntityNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Incorrect username or password.", null));
            
        }
    }

    public void logout(ActionEvent event) throws IOException {
        System.out.println("*** LoginManagedBean.logout()");
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/index.xhtml");
    }

    public void back(ActionEvent event) throws IOException {
        System.out.println("*** LoginManagedBean.back()");
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/home.xhtml");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
