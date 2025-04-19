package model.data;

import java.io.Serializable;

public class SearchCondition implements Serializable{
	
	private String cinemaName;
	private String movieName;
	private String movieTime;
	private int ticketPrice;
	private String url;
	private String genre;
	
	public SearchCondition() {};
	
	public SearchCondition(String cinemaName, String movieName, String movieTime, int ticketPrice) {
		this.cinemaName = cinemaName;
		this.movieName = movieName;
		this.movieTime = movieTime;
		this.ticketPrice = ticketPrice;
	}
	
	//getter
	public String getCinemaName() {
		return cinemaName;
	}	
	

	public String getMovieName() {
		return movieName;
	}
	
	public String getMovieTime() {
		return movieTime;
	}
	
	public int getTicketPrice() {
		return ticketPrice;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getGenre() {
		return genre;
	}
	
	//setter
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	
	public void setMovieTime(String movieTime) {
		this.movieTime = movieTime;
	}
	
	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
}
