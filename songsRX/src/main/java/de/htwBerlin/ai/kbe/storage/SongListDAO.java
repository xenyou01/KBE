package de.htwBerlin.ai.kbe.storage;

import java.util.Collection;

import de.htwBerlin.ai.kbe.bean.SongList;

public interface SongListDAO {
	
	public SongList getSongListById(Integer id);
	
	public Collection<SongList> getAllSongLists(Integer userId, String access);
	
	public Integer addSongList(SongList list);
	
	public SongList deleteSongList(Integer id, String userId);
}
