package jsf.managedbean;

import entity.User;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;



@Named(value = "viewUserDetailsManagedBean")
@ViewScoped

public class ViewUserDetailsManagedBean implements Serializable
{
    private User userEntityToView;
    
    
    
    public ViewUserDetailsManagedBean()
    {
        userEntityToView = new User();
    }
    
    
    
    @PostConstruct
    public void postConstruct()
    {        
    }

    
    
    public User getUserEntityToView() {
        return userEntityToView;
    }

    public void setUserEntityToView(User userEntityToView) {
        this.userEntityToView = userEntityToView;
    }    
}
