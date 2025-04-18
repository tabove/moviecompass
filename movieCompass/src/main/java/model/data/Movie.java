package model.data;

import java.io.Serializable;

public class Movie implements Serializable {
	private String id;
	private String name;
	private String genre;
	private String description;
	private String release_date;
	private int duration;
	
	public Movie() {}
	public Movie(String movie_id, String movie_name, String movie_genre,
					String movie_description, String movie_release_date, int movie_duration) {
		this.id = movie_id;
		this.name = movie_name;
		this.genre = movie_genre;
		this.description = movie_description;
		this.release_date = movie_release_date;
		this.duration = movie_duration;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getGenre() {
		return genre;
	}
	public String getDescription() {
		return description;
	}
	public String getRelease_date() {
		return release_date;
	}
	public int getDuration() {
		return duration;
	}
}
