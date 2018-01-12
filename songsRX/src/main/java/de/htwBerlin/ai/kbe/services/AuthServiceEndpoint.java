package de.htwBerlin.ai.kbe.services;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.Factory;

import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.storage.Token;


@Path("/auth")
public class AuthServiceEndpoint {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response authenticate(@QueryParam("userId") String user_id) {
		if (user_id == null)
			return Response.status(Response.Status.BAD_REQUEST).entity("No user was given").build();
		String chekforToken = Token.getInstance().getToken(user_id);
		if(chekforToken != null)
			return Response.status(Response.Status.OK).entity(chekforToken).build();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("songsDB-PU");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			Query q = em.createQuery("SELECT u FROM User u where userId='" + user_id+"'");
			User user = (User) q.getSingleResult();
			if(user == null)
				return Response.status(Response.Status.FORBIDDEN).entity("User doesn't exist").build();
			String token = Token.getInstance().createToken(user_id);
			return Response.status(Response.Status.OK).entity(token).build();
		} catch (NoResultException e) {
			return Response.status(Response.Status.FORBIDDEN).entity("User doesn't exist").build();
		} 
		catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An internal error has occured. please try again.").build();
		}finally {
			em.close();
			factory.close();
		}
	}

}
