package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;
import java.util.Set;

import de.htwBerlin.ai.kbe.bean.Song;

public interface SongsDAO {
	
	public Song getSong(Integer id);
	
	public Collection<Song> getAllSong();
	
	public Integer addSong(Song song);
	
	public boolean updateSong(Integer id, Song song);
	
	public Song deleteSong(Integer id);

}
