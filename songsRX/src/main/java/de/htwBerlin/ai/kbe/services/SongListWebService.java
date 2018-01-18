package de.htwBerlin.ai.kbe.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.jackson.JacksonFeature;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.bean.SongList;
import de.htwBerlin.ai.kbe.bean.User;
import de.htwBerlin.ai.kbe.filter.Secured;
import de.htwBerlin.ai.kbe.storage.SongListDAO;
import de.htwBerlin.ai.kbe.storage.SongListDAOImpl;
import de.htwBerlin.ai.kbe.storage.Token;
import de.htwBerlin.ai.kbe.storage.UsersDAO;

@Path("/userId")
public class SongListWebService {
	
	@Inject
	UsersDAO userStore;
	
	@Inject
	SongListDAO songListStore;
	
	private static Status NOT_FOUND = Response.Status.NOT_FOUND;
	private static Status OK = Response.Status.OK;
	private static Status SERVER_ERR = Response.Status.INTERNAL_SERVER_ERROR;
	private static Status FORBIDDEN = Response.Status.FORBIDDEN;
	private static Status UNAUTHORIZED = Response.Status.UNAUTHORIZED;
	
	@GET
	@Secured
	@Path("/{userId}/songLists")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getAllSongLists(@PathParam("userId") String userId, @Context HttpHeaders headers) {
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		User oneUser = null;
		try {
			oneUser = findUser(userId);
			if(oneUser == null)
				return Response.status(NOT_FOUND).build();
		} catch (PersistenceException e) {
			return Response.status(SERVER_ERR).build();
		}
		Collection<SongList> list = null;
		int access = 0;
		if(!isSameUser(userId, token)) {
			access = 1;
		}
		try {
			list = songListStore.getAllSongLists(oneUser.getId(), access);
			return Response.status(OK).entity(list).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	@GET
	@Secured
	@Path("/{userId}/songLists/{songListId}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getSong(@PathParam("userId") String userId, @PathParam("songListId") Integer listId, @Context HttpHeaders headers) {
		
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		User oneUser = null;
		try {
			oneUser = findUser(userId);
			if(oneUser == null)
				return Response.status(UNAUTHORIZED).build();
		} catch (PersistenceException e) {
			return Response.status(SERVER_ERR).build();
		}
		int access = 0;
		SongList list = null;
		if(!isSameUser(userId, token))
			access = 1;
		try {
			list = songListStore.getSongListById(listId, userId, access);
			if(list == null)
				return Response.status(NOT_FOUND).build();
			return Response.status(OK).entity(list).build();
		} catch (IllegalAccessError e) {
			return Response.status(FORBIDDEN).build();
		}
		catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	@POST
	@Secured
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{userId}/songLists")
	public Response createSongList(@PathParam("userId") String userId, @Context HttpHeaders headers, @Context UriInfo location, SongList songList) {
		
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		User oneUser = null;
		try {
			oneUser = findUser(userId);
			if(oneUser == null)
				return Response.status(UNAUTHORIZED).entity("User does not exist!").build();
		} catch (PersistenceException e) {
			return Response.status(SERVER_ERR).build();
		}
		if(!isSameUser(userId, token))
			return Response.status(FORBIDDEN).entity("You can not create a playlist for a different user!").build();
		oneUser.addSongList(songList);
		songList.setUser(oneUser);
		try {
			Integer newId = songListStore.addSongList(songList);
			return Response.created(location.getAbsolutePathBuilder().path(newId.toString()).build()).build();
		} catch (ConstraintViolationException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Please check your request Body!").build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Secured
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/{userId}/songLists/{songListId}")
	public Response deleteSongList(@PathParam("userId") String userId, @PathParam("songListId") Integer listId, @Context HttpHeaders headers) {
		
		String token = headers.getHeaderString(HttpHeaders.AUTHORIZATION);
		User oneUser = null;
		try {
			oneUser = findUser(userId);
			if(oneUser == null)
				return Response.status(UNAUTHORIZED).entity("User doesn't exist!").build();
		} catch (PersistenceException e) {
			return Response.status(SERVER_ERR).entity("An error occured while connecting to the Server!").build();
		}
		if(!isSameUser(userId, token))
			return Response.status(FORBIDDEN).entity("Sorry, you can not delete another users playlist!").build();
		try {
			songListStore.deleteSongList(listId, userId);
			return Response.ok().build();
		} catch (IllegalAccessError e) {
			return Response.status(FORBIDDEN).entity("Sorry, you can not delete another users playlist!").build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	
	public boolean isSameUser(String user_id, String token) {
		String userToken = Token.getInstance().getToken(user_id);
		if(userToken == null)
			return false;
		return token.equals(userToken);
	}
	
	public User findUser(String userId) {
		User oneUser = null;
		oneUser = userStore.findUserByUserId(userId);
		return oneUser;
	}
}