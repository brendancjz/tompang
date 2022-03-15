/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Dispute;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author seanang
 */
@Named(value = "viewDisputeDetailsManagedBean")
@ViewScoped
public class ViewDisputeDetailsManagedBean implements Serializable{
    
    private Dispute dispute;
    
  

    /**
     * Creates a new instance of ViewDisputeDetailsManagedBean
     */
    public ViewDisputeDetailsManagedBean() {
        this.dispute = new Dispute();
    }

    /**
     * @return the dispute
     */
    public Dispute getDispute() {
        return dispute;
    }

    /**
     * @param dispute the dispute to set
     */
    public void setDispute(Dispute dispute) {
        this.dispute = dispute;
    }
    
    
    
    
}
