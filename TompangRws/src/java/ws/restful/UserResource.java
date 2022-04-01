/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import ejb.stateless.UserSessionBeanLocal;
import entity.User;
import exception.CreateNewUserException;
import exception.EntityNotFoundException;
import exception.InvalidLoginCredentialsException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import ws.datamodel.CreateUserReq;
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
    
    public UserResource() {
        sessionBeanLookup = new SessionBeanLookup();
        userSessionBean = sessionBeanLookup.lookupUserSessionBeanLocal();
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

}
