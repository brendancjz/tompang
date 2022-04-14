package jsf.managedbean;

import entity.User;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

@Named(value = "viewUserDetailsManagedBean")
@ViewScoped

public class ViewUserDetailsManagedBean implements Serializable {

    private User userEntityToView;

    public ViewUserDetailsManagedBean() {
        userEntityToView = new User();
    }

    public void viewUserListings(ActionEvent event) throws IOException {
        System.out.println("******* ViewUserDetailsManagedBean.viewUserListings()");
        String username = (String)event.getComponent().getAttributes().get("username");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("username", username);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewAllListings.xhtml");
    }

    public User getUserEntityToView() {
        return userEntityToView;
    }

    public void setUserEntityToView(User userEntityToView) {
        this.userEntityToView = userEntityToView;
    }
}
