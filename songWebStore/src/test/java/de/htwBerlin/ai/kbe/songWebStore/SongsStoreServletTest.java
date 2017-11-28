package de.htwBerlin.ai.kbe.songWebStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

import com.fasterxml.jackson.core.JsonGenerator;
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
    		request = new MockHttpServletRequest();
    		response = new MockHttpServletResponse();
    		servlet = new SongsStoreServlet();
    		config = new MockServletConfig();
    		config.addInitParameter("signature", "Personaly");
    		config.addInitParameter("songFilename", "songs.json");
    }
    
    /*@SuppressWarnings("unchecked")
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
    
    @Test
    public void initShouldLoadSongList() throws ServletException, IOException {
    	servlet.init(config);
    	request.addParameter("all", "");
    	servlet.doGet(request, response);
    	List<Song> songList = (List<Song>) objectMapper.readValue(response.getContentAsByteArray(), new TypeReference<List<Song>>(){});
    	assertTrue(songList.size() >0);
    }

	@Test
    public void doGetShouldReturnAllSongAndStatusOKWhenParameterIstAll() throws IOException, ServletException {
		servlet.init(config);
    	request.addParameter("all", "");
    	request.setContentType(MediaType.TEXT_PLAIN_VALUE);
    	servlet.doGet(request, response);
    	assertEquals(response.getStatus(), 200);
    	assertEquals(response.getContentType(), "application/json");
    }
    
	@Test
    public void doGetShouldReturnTheSongWithIDIfExist() throws IOException, ServletException {
		servlet.init(config);
    	request.addParameter("songId", "6");
    	request.setContentType(MediaType.TEXT_PLAIN_VALUE);
    	servlet.doGet(request, response);
    	assertEquals(response.getStatus(), 200);
    	assertEquals(response.getContentType(), "application/json");
    	Song returnedSong = (Song) objectMapper.readValue(response.getContentAsByteArray(), Song.class);
    	assertEquals(returnedSong.getId(), new Integer(6));
    }
	
	@Test
    public void doGetShouldReturnErrorCodeIfNoSongWithTheGivenId() throws IOException, ServletException {
		servlet.init(config);
    	request.addParameter("songId", "-1000");
    	request.setContentType(MediaType.TEXT_PLAIN_VALUE);
    	servlet.doGet(request, response);
    	assertEquals(response.getStatus(), 400);
    }
    
    
    @Test
    public void doPostShouldAddSingleSongAndReturnCreatedCode() throws ServletException, IOException {
    	servlet.init(config);
    	request.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	Song song = new Song (null, "titleXX", "artistXX", "albumXX", 1999);
    	request.setContent(objectMapper.writeValueAsBytes(song));
    	servlet.doPost(request, response);
    	assertEquals(response.getStatus(), 201);
    }
    
    @Test
    public void doPostShouldAddListOfSongAndReturnCreatedCode() throws ServletException, IOException {
    	servlet.init(config);
    	request.setContentType(MediaType.APPLICATION_JSON_VALUE);
    	Song song = new Song (null, "titleXX", "artistXX", "albumXX", 1999);
    	Song song1 = new Song (null, "titleYY", "artistYY", "albumYY", 1998);
    	Song song2 = new Song (null, "titleZZ", "artistZZ", "albumZZ", 1997);
    	List<Song> songList = new ArrayList<>();
    	songList.add(song);songList.add(song1);songList.add(song2);
    	request.setContent(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(songList));
    	servlet.doPost(request, response);
    	assertEquals(response.getStatus(), 201);
    }
    
    @Test
    public void doDeleteShouldReturnForbiddenCode() throws IOException {
    	servlet.doDelete(request, response);
    	assertEquals(response.getStatus(), 403);
    }
    
    @Test
    public void doPutShouldReturnForbiddenCode() throws IOException {
    	servlet.doPut(request, response);
    	assertEquals(response.getStatus(), 403);
    }*/
    
    @Test
    public void play() throws FileNotFoundException, URISyntaxException
    {
    	//InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongs.json");
    	//System.out.println(this.getClass().getClassLoader().getResource("testSongs.json").toURI());
    	FileOutputStream fos =new FileOutputStream(new File(this.getClass().getClassLoader().getResource("testSongs.json").getPath()));
    }
}
