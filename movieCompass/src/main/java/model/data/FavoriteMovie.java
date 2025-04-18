package model.data;

import java.io.Serializable;

public class FavoriteMovie implements Serializable {
	private String id;
	private String movie_id;
	private String movie_name;
	private String torokubi;
	
	public FavoriteMovie() {}
	public FavoriteMovie(String id, String movie_id, String movie_name, String torokubi) {
		this.id = id;
		this.movie_id = movie_id;
		this.movie_name = movie_name;
		this.torokubi = torokubi;
	}
	public FavoriteMovie(String id, String movie_id, String movie_name) {
		this(id, movie_id, movie_name, "");
	}
	
	public String getId() {
		return id;
	}
	public String getMovie_id() {
		return movie_id;
	}
	public String getMovie_name() {
		return movie_name;
	}
	public String getTorokubi() {
		return torokubi;
	}

}
