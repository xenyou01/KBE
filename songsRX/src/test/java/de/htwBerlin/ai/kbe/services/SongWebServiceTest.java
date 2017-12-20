package de.htwBerlin.ai.kbe.services;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import de.htwBerlin.ai.kbe.bean.Song;

public class SongWebServiceTest extends JerseyTest {
	
	@Override
	protected Application configure() {
		return new ResourceConfig(SongWebService.class);
	}	
	 
	@Test
    public void testDeleteWithUnknownSongShouldReturn404(){
        Response output = target("/songs/777").request().delete();
        assertEquals("Should return status 404", 404, output.getStatus());
    }
	
	@Test
    public void testDeleteWithKnownSongShouldReturn204(){
        Response output = target("/songs/10").request().delete();
        assertEquals("Should return status 204", 204, output.getStatus());
    }
	
	@Test
    public void testPutWithUnknownSongIdShouldReturn404(){
		Song song = new Song.Builder(777, "New Title").build();		
        Response output = target("/songs/777")
        		.request()
        		.put(Entity.entity(song, MediaType.APPLICATION_JSON));;
        assertEquals("Should return status 404", 404, output.getStatus());
    }
	
	@Test
    public void testPutWithKnownSongIdShouldReturn204(){
		Song song = new Song.Builder(10, "New Title").build();
		Response output = target("/songs/10")
	        		.request()
	        		.put(Entity.entity(song, MediaType.APPLICATION_JSON));;
	    assertEquals("Should return status 204", 204, output.getStatus());
    }
	
	@Test
    public void testPutWithUnknownSongIdAndNoTitleShouldReturn406(){
		Song song = new Song();		
        Response output = target("/songs/777")
        		.request()
        		.put(Entity.entity(song, MediaType.APPLICATION_JSON));;
        assertEquals("Should return status 406", 406, output.getStatus());
    }
	
	@Test
    public void testPutWithKnownSongIdAndNoTitleShouldReturn406(){
		Song song = new Song();
		Response output = target("/songs/10")
	        	.request()
	        	.put(Entity.entity(song, MediaType.APPLICATION_JSON));;
	    assertEquals("Should return status 406", 406, output.getStatus());
    }

}
