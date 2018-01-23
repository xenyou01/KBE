package de.htwBerlin.ai.kbe.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="user")
@XmlRootElement(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	private String email;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval=true)
	//@JsonIgnore
	private Set<SongList> songLists;

	public User() {
		this.songLists = new HashSet<>();
	}

	public User(String userId, String firstName, String lastName, String email) {
		this();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public String getUserId() {
		return userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String setFirstName(String firstName) {
		return this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
	
	public Set<SongList> getSongLists() {
		return songLists;
	}

	public void setSongLists(Set<SongList> songLists) {
		this.songLists = songLists;
	}
	
	public void addSongList(SongList songList) {
		this.songLists.add(songList);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + "]";
	}
}
