package de.htwBerlin.ai.kbe.songWebStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.omg.CORBA.RepositoryIdHelper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(
	name = "SongsServlet", 
	urlPatterns = "/*",
			initParams = {
					@WebInitParam(name = "signature", 
					    value = "Thanks for using AI-KBE's Song Webstore © 2017! ")
			}
)
public class SongsStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String APPLICATION_JSON = "application/json";
	
	private static final String TEXT_PLAIN = "text/plain";
	
	private String mySignature = null;
	
    private String songFilename = null;
	
    private ConcurrentHashMap<Integer, Song> songStore = null;
    
    private AtomicInteger currentID = null;
    private ObjectMapper objectMapper = null;

	// load songStore from JSON file and set currentID
	public void init(ServletConfig servletConfig) throws ServletException {
		
		this.mySignature = servletConfig.getInitParameter("signature");
		objectMapper = new ObjectMapper().enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		songFilename = "songs.json";
		songStore = new ConcurrentHashMap<>();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);
		
		try {
			List<Song> songList = (List<Song>) objectMapper.readValue(input, new TypeReference<List<Song>>(){});
			currentID = new AtomicInteger(songList.get(0).getId());
			for(Song song : songList){
	    		songStore.put(song.getId(), song);
	    		if(currentID.get() < song.getId().intValue())
	    			currentID.set(song.getId().intValue());
	    	}
		} catch (IOException e) {
		}finally {
			if(songStore.size() == 0){
				currentID = new AtomicInteger(1);
			}
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
				}
		}
	}

    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,String[]> parameter = request.getParameterMap();
		try {
			if(parameter.containsKey("all")){
				response.setContentType(APPLICATION_JSON);
				response.setStatus(200);
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), songStore.values());
				response.getOutputStream().println("\n"+mySignature);
			}
			else if(parameter.containsKey("songId")){
				String[] ids = parameter.get("songId");
				List<Integer> idsCast = new ArrayList<>();
				for(String id : ids){
					try {
						Integer idNumber = new Integer(id);
						if(idsCast.contains(idNumber)){
							continue;
						}
						idsCast.add(idNumber);
					} catch (NumberFormatException e) {
						continue;
					}
				}
				if(idsCast.size() == 0){
					sendResponse(response, 400, TEXT_PLAIN, "The specified Id(s) could'nt be read");
					return;
				}
				List<Song> songWithGivenId = new ArrayList<>();
				for(Integer id : idsCast){
					Song song;
					if((song = songStore.get(id)) != null){
						songWithGivenId.add(song);
					}
				}
				if(songWithGivenId.size() == 0){
					sendResponse(response, 400, TEXT_PLAIN, "No song with the given id(s)");
					return;
				}
				response.setContentType(APPLICATION_JSON);
				int status = songWithGivenId.size() == ids.length  ? 200 : 206;
				response.setStatus(status);
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), songWithGivenId);
			}
			else{
				sendResponse(response, 400, TEXT_PLAIN, "Please check the parameters!");
			}
		} catch (JsonGenerationException e) {
			sendResponse(response, 500, TEXT_PLAIN, "Internal Server ERROR");
		} catch (JsonMappingException e) {
			sendResponse(response, 500, TEXT_PLAIN, "Internal Server ERROR");
		} catch (IOException e) {
			sendResponse(response, 500, TEXT_PLAIN, "Internal Server ERROR");
		}
		
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Song> songList;
		String payload = "";
		try {
				songList = (List<Song>) objectMapper.readValue(request.getInputStream(), new TypeReference<List<Song>>(){});
				for(Song song : songList){
					if(song.getTitle() == null || song.getArtist() == null || song.getAlbum() == null  || song.getReleased() == null){
						sendResponse(response, 400, TEXT_PLAIN, "One or more of the given had one or more missing informations. Please check if you passed all necessary Informations");
						return;
					}
		    	}
				for(Song song : songList){
					song.setId(currentID.get() + 1);
					currentID.set(currentID.get() +1 );
					songStore.put(song.getId(), song);
					payload +="Added song '" + song.getTitle() + "', id=" + song.getId() + "\n";
				}
				sendResponse(response, 201, TEXT_PLAIN, payload);
		} catch (IOException e) {
			sendResponse(response, 500, TEXT_PLAIN, "Sorry, but I couldn't parse your payload");
		}
	}
	
	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		sendResponse(response, 403, TEXT_PLAIN, "You should not use this method yet");
	}	
	
	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		sendResponse(response, 403, TEXT_PLAIN, "Sorry, you can't delete a ressource");
	}
	
	public void sendResponse(HttpServletResponse response, int status, String contentType, String body) throws IOException{
		response.setContentType(contentType);
		response.setStatus(status);
		response.getOutputStream().println(body);
		response.getOutputStream().println(mySignature);
	}
	
	// save songStore to file
	@Override
	public void destroy() {
		try {
			
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream(songFilename), songStore.values());
		} catch (IOException e) {
		}
	}
}
