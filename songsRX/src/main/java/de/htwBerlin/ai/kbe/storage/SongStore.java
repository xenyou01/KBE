package de.htwBerlin.ai.kbe.storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.bean.Song.Builder;

public class SongStore {
	
	private static SongStore instance = null;
	private static HashMap<Integer, Song> songStorage;
	
	private SongStore()
	{
		songStorage = new HashMap<Integer, Song>();
		loadSongs();
	}
	
	public synchronized static SongStore getInstance()
	{
		if(instance == null)
			instance = new SongStore();
		return instance;
	}
	
	public Song getSong(Integer id){
		return songStorage.get(id);
	}
	
	public Collection<Song> getAllSong(){
		return songStorage.values();
	}
	
	public synchronized Integer addSong(Song song){
		if(song.getId() != null){
			if((songStorage.get(song.getId()) != null)){
				return null;
			}
			songStorage.put(song.getId(), song);
			return song.getId();
		}
		int currentId = songStorage.keySet().stream().max((key1, key2) -> key1.intValue() > key2.intValue() ? 1 : -1).get().intValue();
		song.setId(new Integer(currentId+1));
		songStorage.put(song.getId(), song);
		return song.getId();
	}
	
	public boolean updateSong(Song song){
		if(song == null)
			return false;
		Integer id = null;
		if((id = song.getId()) != null){
			Song s = songStorage.replace(id, song);
			if(s != null)
				return true;
		}
		return false;
	}
	
	public Song deleteSong(Integer id){
		if(id == null)
			return null;
		return songStorage.remove(id);
	}
	
	private void loadSongs(){
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("songs.json");
		try {
			List<Song> songs = (List<Song>) objectMapper.readValue(is, new TypeReference<List<Song>>(){});
			for(Song song : songs)
				songStorage.put(song.getId(), song);
		} catch (IOException e) {
			System.out.println("oups!!");
			return;
		}
	}

}
