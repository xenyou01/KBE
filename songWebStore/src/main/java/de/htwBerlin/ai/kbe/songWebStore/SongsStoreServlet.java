package de.htwBerlin.ai.kbe.songWebStore;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(
	name = "SongsServlet", 
	urlPatterns = "/songWebStore",
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
		objectMapper = new ObjectMapper();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String,String[]> parameter = request.getParameterMap();
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
					idsCast.add(idNumber);
				} catch (NumberFormatException e) {
					continue;
				}
			}
			if(idsCast.size() == 0){
				response.setStatus(400);
				response.setContentType(TEXT_PLAIN);
				response.getOutputStream().println("The specified Id(s) could'nt be read");
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
				response.setStatus(400);
				response.setContentType(TEXT_PLAIN);
				response.getOutputStream().println("No song with the given id(s)");
				return;
			}
			response.setContentType(APPLICATION_JSON);
			int status = songWithGivenId.size() == ids.length  ? 200 : 206;
			response.setStatus(status);
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(), songWithGivenId);
		}
		else{
			response.setContentType(TEXT_PLAIN);
			response.setStatus(400);
			response.getOutputStream().println("Please check the parameters!");
			response.getOutputStream().println(mySignature);
		}
		
	}

	
//	@Override
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}
	
//	@Override
//	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}	
	
//	@Override
//	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}
	
	// save songStore to file
	@Override
	public void destroy() {
		System.out.println("In destroy");
	}
}
