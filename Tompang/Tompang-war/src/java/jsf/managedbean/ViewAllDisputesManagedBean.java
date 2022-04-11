/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import entity.Dispute;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import ejb.stateless.DisputeSessionBeanLocal;

/**
 *
 * @author seanang
 */
@Named(value = "viewAllDisputesManagedBean")
@ViewScoped
public class ViewAllDisputesManagedBean implements Serializable{

    @EJB
    private DisputeSessionBeanLocal disputeSessionBean;
    
    @Inject
    private ViewDisputeDetailsManagedBean viewDisputeDetailsManagedBean;
    
    private List<Dispute> disputes;
    private List<Dispute> filteredDisputes;

    /**
     * Creates a new instance of ViewAllDisputesManagedBean
     */
    public ViewAllDisputesManagedBean() {
    }
    
     @PostConstruct
    public void retrieveAllDisputes() {
  
        try {
            setDisputes(disputeSessionBean.retrieveAllDisputes());
        } catch (EmptyListException ex) {
            System.out.println("Unable to retrieve list of users.");
        }
    }
    
    public void resolveDispute(AjaxBehaviorEvent event){
        
        Long disputeId = (Long)event.getComponent().getAttributes().get("disputeId");
        System.out.println("Resolving dispute..." + disputeId);
        try{
            disputeSessionBean.resolveDispute(disputeId);
            this.retrieveAllDisputes();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully resolved dispute.", null));
        }catch(EntityNotFoundException ex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Resolving dispute unsuccessful.", null));
        }
    }

    public List<Dispute> getDisputes() {
        return disputes;
    }

    public void setDisputes(List<Dispute> disputes) {
        this.disputes = disputes;
    }

    public ViewDisputeDetailsManagedBean getViewDisputeDetailsManagedBean() {
        return viewDisputeDetailsManagedBean;
    }

    public void setViewDisputeDetailsManagedBean(ViewDisputeDetailsManagedBean viewDisputeDetailsManagedBean) {
        this.viewDisputeDetailsManagedBean = viewDisputeDetailsManagedBean;
    }

    public List<Dispute> getFilteredDisputes() {
        return filteredDisputes;
    }

    public void setFilteredDisputes(List<Dispute> filteredDisputes) {
        this.filteredDisputes = filteredDisputes;
    }
    
    
    
}
