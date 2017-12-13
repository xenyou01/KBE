package de.htwBerlin.ai.kbe.bean;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "song")
public class Song {

	private Integer id;
	private String title;
	private String artist;
	private String album;
	private Integer released;
	
	public Song () {	}
	
	public static class Builder
	{
		private Integer id;
		private String title;
		private String artist;
		private String album;
		private Integer released;
		
		public Builder(Integer id, String title)
		{
			this.id = id;
			this.title = title;
		}
		
		public Builder artist(String artist){
			this.artist = artist;
			return this;
		}
		
		public Builder album(String album){
			this.album = album;
			return this;
		}
		
		public Builder released(Integer released)
		{
			this.released = released;
			return this;
		}
		
		public Song build(){
			return new Song(this);
		}
	}
	
	public Song (Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.artist = builder.artist;
		this.album = builder.album;
		this.released = builder.released;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public void setAlbum(String album) {
		this.album = album;
	}
	
	public Integer getReleased() {
		return released;
	}
	
	public void setReleased(Integer released) {
		this.released = released;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", title=" + title + ", artist=" + artist + ", album=" + album + ", released="
				+ released + "]";
	}
	
	
	
	/*@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.id == ((Song)other).id && this.title.equals(((Song)other).title) && this.album.equals(((Song)other).album) && this.artist.equals(((Song)other).artist) && this.released == ((Song)other).released)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.id ^ (this.id >>> 32));
	}*/
}
