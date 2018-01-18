package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.bean.SongList;

public interface SongListDAO {
	
	public SongList getSongListById(Integer id, String userId, int access);
	
	public Collection<SongList> getAllSongLists(Integer userId, int access);
	
	public Integer addSongList(SongList list);
	
	public SongList deleteSongList(Integer id, String userId);
}
