package model.data;

import java.io.Serializable;

public class SearchCondition implements Serializable{
	
	private String cinema_id;
	private String cinema_name;
	private String movie_id;
	private String movie_name;
	private String movie_time;
	private String date;
	private String dateTime;
	private int ticketPrice;
	private String url;
	private String genre;
	
	public SearchCondition() {};
	
	public SearchCondition(String cinema_id, String cinema_name, String movie_id, String movie_name, String genre, String date, String dateTime) {
		this.cinema_id = cinema_id;
		this.cinema_name = cinema_name;
		this.movie_id = movie_id;
		this.movie_name = movie_name;
		this.genre = genre;
		this.date = date;
		this.dateTime = dateTime;
	}
	
	//getter
	public String getCinemaId() {
		return cinema_id;
	}
	
	public String getCinemaName() {
		return cinema_name;
	}	
	
	public String getMovie_id() {
		return movie_id;
	}

	public String getMovieName() {
		return movie_name;
	}
	
	public String getMovieTime() {
		return movie_time;
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
	public void setCinemaId(String cinema_id) {
		this.cinema_id = cinema_id;
	}

	public void setCinemaName(String cinema_name) {
		this.cinema_name = cinema_name;
	}
	
	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}
	
	public void setMovieName(String movie_name) {
		this.movie_name = movie_name;
	}
	
	public void setMovieTime(String movie_time) {
		this.movie_time = movie_time;
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
	
	public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public String getDateTime() {
        return dateTime;
    }
    
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
