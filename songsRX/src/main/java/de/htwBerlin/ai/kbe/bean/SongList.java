package de.htwBerlin.ai.kbe.bean;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "songlist")
@XmlRootElement(name = "songlist")
public class SongList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer access;
	
	@ManyToOne
	@JoinColumn(name = "owner")
	private User user;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "playlistEntries",
		joinColumns = @JoinColumn(name = "list_id", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
		foreignKey = @ForeignKey(name = "id"),
		inverseForeignKey = @ForeignKey(name = "id")
	)
	@JsonIgnore
	private Set<Song> songs;
	
	public SongList() {
		songs = new HashSet<Song>();
	}
	
	public SongList(User user, Integer access, Collection<Song> songs) {
		this();
		this.user = user;
		this.access = access;
		this.songs.addAll(songs);
	}

	public Set<Song> getSongs() {
		return songs;
	}
	
	public void setSongs(Set<Song> songs) {
		this.songs.addAll(songs);
	}

	/*public void addSongs(Collection<Song> songs) {
		this.songs.addAll(songs);
	}*/
	
	/*public void addSongs(Song song) {
		this.songs.add(song);
	}*/

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public Integer getAccess() {
		return access;
	}
	
	public void setAccess(Integer access) {
		this.access = access;
	}
	
	/*
	@Override
	public String toString() {
		return "SongList [Id=" + Id + ", user=" + user + ", access=" + access + ", songs=" + songs + "]";
	}*/
	
	

}
