package de.htwBerlin.ai.kbe.services;

import java.util.Collection;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.filter.Secured;
import de.htwBerlin.ai.kbe.storage.SongStore;
import de.htwBerlin.ai.kbe.storage.SongsDAO;
import de.htwBerlin.ai.kbe.storage.Token;

@Path("/songs")
public class SongWebService {
	
	@Inject
	SongsDAO songStore;

	@GET
	@Secured
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Collection<Song> getAllSongs()
	{
		return songStore.getAllSong();
	}
	
	@GET
	@Secured
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getSong(@PathParam("id") Integer id){
		Song song = songStore.getSong(id);
		if(song != null){
			return Response.ok(song).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	
	@POST
	@Secured
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_PLAIN)
	public Response addSong(Song song){
		if(song == null || song.getTitle() == null || song.getAlbum()== null || song.getArtist()==null||song.getReleased()==null){
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("All fields should be set!").build();
		}
		song.setId(null);
		try {
			Integer id = songStore.addSong(song);
			if(id == null)
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("error here???????????").build();
			return Response.status(Response.Status.CREATED).entity(id).build();
		} catch (Exception e) {
			return Response.serverError().entity("Error while uploadind the song. Please try again").build();
		}
	}
	
	@PUT
	@Secured
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateSong(@PathParam("id") Integer id, Song song)
	{
		if(song == null || id ==null || song.getTitle() == null || song.getAlbum()== null || song.getArtist()==null||song.getReleased()==null)
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		try {
			boolean existingSong = songStore.updateSong(id, song);
			if(!existingSong){
				return Response.status(Response.Status.NOT_FOUND).build();
			}
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
	
	@DELETE
	@Secured
	@Path("/{id}")
	public Response deleteSong(@PathParam("id") Integer id){
		if(id == null)
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		try {
			Song deletedSong = songStore.deleteSong(id);
			if(deletedSong == null)
				return Response.status(Response.Status.NOT_FOUND).build();
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			return Response.serverError().build();
		}
	}
}
