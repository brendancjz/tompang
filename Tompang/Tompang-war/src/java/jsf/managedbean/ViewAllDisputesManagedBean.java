/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.DisputeSessionBeanLocal;
import entity.Dispute;
import exception.EmptyListException;
import exception.EntityNotFoundException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

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

    /**
     * Creates a new instance of ViewAllDisputesManagedBean
     */
    public ViewAllDisputesManagedBean() {
    }
    
     @PostConstruct
    public void retrieveAllUsers() {
  
        try {
            setDisputes(disputeSessionBean.retrieveAllDisputes());
        } catch (EmptyListException ex) {
            System.out.println("Unable to retrieve list of users.");
        }
    }
    
    public void resolveDispute(ActionEvent event){
        Long disputeId = (Long)event.getComponent().getAttributes().get("disputeId");
        try{
            Dispute dispute = disputeSessionBean.getDisputeByDisputeId(disputeId);
            disputeSessionBean.resolveDispute(dispute);
        }catch(EntityNotFoundException ex){
            
        }
    }

    /**
     * @return the viewDisputeDetailsManagedBean
     */
    public ViewDisputeDetailsManagedBean getViewDisputeDetailsManagedBean() {
        return viewDisputeDetailsManagedBean;
    }

    /**
     * @param viewDisputeDetailsManagedBean the viewDisputeDetailsManagedBean to set
     */
    public void setViewDisputeDetailsManagedBean(ViewDisputeDetailsManagedBean viewDisputeDetailsManagedBean) {
        this.viewDisputeDetailsManagedBean = viewDisputeDetailsManagedBean;
    }

    /**
     * @return the disputes
     */
    public List<Dispute> getDisputes() {
        return disputes;
    }

    /**
     * @param disputes the disputes to set
     */
    public void setDisputes(List<Dispute> disputes) {
        this.disputes = disputes;
    }
    
}
