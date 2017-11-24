package de.htwBerlin.ai.kbe.songWebStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SongsStoreServletTest {
	
    private SongsStoreServlet servlet;
    private MockServletConfig config;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private 	ObjectMapper objectMapper;
    
    @Before
    public void setUp() throws ServletException {
    		objectMapper = new ObjectMapper();
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void playingWithJackson() throws IOException {
	    // songs.json and testSongs.json contain songs from this Top-10:
	    // http://time.com/collection-post/4577404/the-top-10-worst-songs/
	    
    	    // Read a JSON file and create song list:
    		InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongs.json");

    		List<Song> songList = (List<Song>) objectMapper.readValue(input, new TypeReference<List<Song>>(){});
	    
	    	// write a list of objects to a JSON-String with prettyPrinter
	    	String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(songList);
	    
	    	// write a list of objects to an outputStream in JSON format
	    	//File file = new File(this.getClass().getClassLoader().getResource("testSongs.json").getFile());
	    	//objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(file), songList);
    
	    	// Create a song and write to JSON
	    	Song song = new Song (null, "titleXX", "artistXX", "albumXX", 1999);
	    byte[] jsonBytes = objectMapper.writeValueAsBytes(song);
	    
	    // Read JSON from byte[] into Object
	    Song newSong = (Song) objectMapper.readValue(jsonBytes, Song.class);
	    assertEquals(song.getTitle(), newSong.getTitle());
	    assertEquals(song.getArtist(), newSong.getArtist());
    }
    
    //@Test
    public void initShouldLoadSongList() {
    }

	//@Test
    public void doGetShouldxxx() {
    }
    
    //@Test
    public void doPostShouldxxx() {      
    }
}
