/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.ListingSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.Listing;
import entity.User;
import exception.InvalidLoginCredentialsException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * REST Web Service
 *
 * @author GuoJun
 */
@Path("Listing")
public class ListingResource {

    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    private final ListingSessionBeanLocal listingSessionBean;
    private final UserSessionBeanLocal userSessionBean;
    
    public ListingResource() {
        sessionBeanLookup = new SessionBeanLookup();
        
        userSessionBean = sessionBeanLookup.lookupUserSessionBeanLocal();
        listingSessionBean = sessionBeanLookup.lookupListingSessionBeanLocal();
    }
    
    @Path("retrieveAllListings")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllListings(@QueryParam("username") String username, @QueryParam("password") String password) {
        try {
             User user = userSessionBean.userLogin(username, password);
             System.out.println("********** ListingResource.retrieveAllProducts(): User " + user.getUsername() + " login remotely via web service");
        
            List<Listing> listings = listingSessionBean.retrieveAllListings();
            GenericEntity<List<Listing>> genericEntity = new GenericEntity<List<Listing>>(listings) {
            };
            
            return Response.status(Status.OK).entity(genericEntity).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
        
    }
}
