package model.data;

import java.io.Serializable;

public class FavoriteCinema implements Serializable {
	private String id;
	private String cinema_id;
	private String cinema_name;
	private String torokubi;
	
	public FavoriteCinema() {}
	public FavoriteCinema(String id, String cinema_id, String cinema_name, String torokubi) {
		this.id = id;
		this.cinema_id = cinema_id;
		this.cinema_name = cinema_name;
		this.torokubi = torokubi;
	}
	public FavoriteCinema(String id, String cinema_id, String cinema_name) {
		this(id, cinema_id, cinema_name, "");
	}
	
	public String getId() {
		return id;
	}
	public String getCinema_id() {
		return cinema_id;
	}
	public String getCinema_name() {
		return cinema_name;
	}
	public String getTorokubi() {
		return torokubi;
	}
	

}
