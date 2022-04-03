/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.CreditCardSessionBeanLocal;
import ejb.stateless.UserSessionBeanLocal;
import entity.CreditCard;
import entity.Listing;
import entity.User;
import exception.CreateNewCreditCardException;
import exception.CreateNewUserException;
import exception.EntityNotFoundException;
import exception.InvalidLoginCredentialsException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import ws.datamodel.UpdateUserReq;

/**
 *
 * @author GuoJun
 */
@Path("User")
public class UserResource {
    @Context
    private UriInfo context;
    
    private final SessionBeanLookup sessionBeanLookup;
    
    
    private final UserSessionBeanLocal userSessionBean;
    
    private final CreditCardSessionBeanLocal creditCardSessionBean;
    
    public UserResource() {
        sessionBeanLookup = new SessionBeanLookup();
        userSessionBean = sessionBeanLookup.lookupUserSessionBeanLocal();
        creditCardSessionBean = sessionBeanLookup.lookupCreditCardSessionBeanLocal();
    }
    
    
    @Path("retrieveUser/{username}")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveUser(@PathParam("username") String username) {
        try {
            User user = userSessionBean.retrieveUserByUsername(username);
            
            // not supposed to be setting null, same problem as ListingResource
            user.setFollowers(null);
            user.setFollowing(null);
            user.setCreatedListings(null);
            user.setLikedListings(null);


            if (user.getFollowers() != null) {
                for (User follower : user.getFollowers()) {
                    follower.getFollowing().clear();
                }
            }
            
            if (user.getFollowing() != null) {
                for (User following : user.getFollowing()) {
                    following.getFollowers().clear();
                }
            }
            
            if (user.getCreatedListings() != null) {
                for (Listing createdListing : user.getCreatedListings()) {
                    createdListing.setCreatedBy(null);
                }
            }
            
            if (user.getLikedListings() != null) {
                for (Listing likedListing : user.getLikedListings()) {
                    likedListing.setLikedByUsers(null);
                }
            }
            
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (EntityNotFoundException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            
        }
    }
    
    
    @Path("userLogin")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@QueryParam("username") String username, 
                                @QueryParam("password") String password) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");
            
            user.setPassword(null);
            user.setSalt(null);
            
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user)
    {
       if(user != null){
            try
            {
                
                Long userId = userSessionBean.createNewUser(user);
                return Response.status(Response.Status.OK).entity(userId).build();
            }
            catch(CreateNewUserException ex)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            catch(Exception ex)
            {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        }  else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update product request").build();
        }
     
   }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateUserReq updateUserReq)
    {
        if(updateUserReq != null)
        {
            try
            {                
                User user = userSessionBean.userLogin(updateUserReq.getUsername(), updateUserReq.getPassword());
                userSessionBean.updateUserDetails(updateUserReq.getUser());
                
                return Response.status(Response.Status.OK).build();
            }
            catch(InvalidLoginCredentialsException ex)
            {
                return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
            }
            catch(EntityNotFoundException ex)
            {
                return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
            }
            catch(Exception ex)
            {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
            }
        }
        else
        {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid update product request").build();
        }
    }
    
    @Path("creditCards")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCreditCards(@QueryParam("username") String username, 
                                @QueryParam("password") String password) {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");
            
            List<CreditCard> creditCards = user.getCreditCards();
          
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCreditCard(@QueryParam("username") String username, 
                                @QueryParam("password") String password, CreditCard card)
    {
       try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");
            
            Long ccId = creditCardSessionBean.createNewCreditCard(card, user.getUserId());
            
            
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch(CreateNewCreditCardException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
     
   }
    
    @Path("creditCards/{ccId}")
    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCreditCard(@QueryParam("username") String username, 
                                        @QueryParam("password") String password,
                                        @PathParam("ccId") Long ccId)
    {
        try {
            System.out.println("*********** " + username + " " + password);
            User user = userSessionBean.userLogin(username, password);
            System.out.println("********** UserResource.userLogin(): User " + user.getUsername() + " login remotely via web service");
            
            creditCardSessionBean.deleteCreditCard(ccId, user.getUserId());
            
            
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (InvalidLoginCredentialsException ex) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build();
        } catch(EntityNotFoundException ex){
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        
    }
    
    
    
    

    private UserSessionBeanLocal lookupUserSessionBeanLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (UserSessionBeanLocal) c.lookup("java:global/Tompang/Tompang-ejb/UserSessionBean!ejb.stateless.UserSessionBeanLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    

}
