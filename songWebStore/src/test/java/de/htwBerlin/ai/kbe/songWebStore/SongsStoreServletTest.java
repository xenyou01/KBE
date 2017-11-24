package de.htwBerlin.ai.kbe.songWebStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    
   /* @Before
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
	    	objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream("output.json"), songList);
    
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
    }*/
    
    @Test
    public void test1(){
    	ObjectMapper objectMapper;
		objectMapper = new ObjectMapper();
		String songFilename = "testSongs.json";
		ConcurrentHashMap<AtomicInteger, Song>songStore = new ConcurrentHashMap<>();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);
		AtomicInteger currentID;
		try {
			List<Song> songList = (List<Song>) objectMapper.readValue(input, new TypeReference<List<Song>>(){});
			currentID = new AtomicInteger(-1);
			for(Song song : songList){
				if(song.getId() == null){
					System.out.println("JA!");
					song.setId(10);
				}
	    		songStore.put(new AtomicInteger(song.getId()), song);
	    		if(currentID.get() < song.getId())
	    			currentID.set(song.getId());
	    	}
			System.out.println(currentID);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(System.out, songList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
