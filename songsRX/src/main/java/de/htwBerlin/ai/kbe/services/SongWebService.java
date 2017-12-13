package de.htwBerlin.ai.kbe.services;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.storage.SongStore;

@Path("/songs")
public class SongWebService {
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Collection<Song> getAllCOntacts()
	{
		return SongStore.getInstance().getAllSong();
	}
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getSong(@PathParam("id") Integer id){
		Song song = SongStore.getInstance().getSong(id);
		if(song != null){
			return Response.ok(song).build();
		}
		return Response.status(Response.Status.NOT_FOUND).entity("No Song with the given ID").build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Produces(MediaType.TEXT_PLAIN)
	public Response addSong(Song song){
		Integer id = SongStore.getInstance().addSong(song);
		if(id == null)
			return Response.status(Response.Status.CONFLICT).entity("!! A song with the same id already exist !!").build();
		return Response.status(Response.Status.CREATED).entity(id).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response updateSong(@PathParam("id") Integer id, Song song)
	{
		song.setId(id);
		boolean existingSong = SongStore.getInstance().updateSong(song);
		if(!existingSong){
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteSong(@PathParam("id") Integer id){
		Song deletedSong = SongStore.getInstance().deleteSong(id);
		if(deletedSong == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	
	

}
