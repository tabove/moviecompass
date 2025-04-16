package model;

import java.io.Serializable;

public class SearchCondition implements Serializable{
	
	private String cinemaName;
	private String titleName;
	private int movieTime;
	private int ticketPrice;
	private String url;
	
	public SearchCondition() {};
	
	public SearchCondition(String cinemaName, String titleName, int movieTime, int ticketPrice) {
		this.cinemaName = cinemaName;
		this.titleName = titleName;
		this.movieTime = movieTime;
		this.ticketPrice = ticketPrice;
	}
	
	//getter
	public String getCinemaName() {
		return cinemaName;
	}	
	

	public String getTitleName() {
		return titleName;
	}
	
	public int getMovieTime() {
		return movieTime;
	}
	
	public int getTicketPrice() {
		return ticketPrice;
	}
	
	public String getUrl() {
		return url;
	}
	
	//setter
	public void setCinemaName(String cinemaName) {
		this.cinemaName = cinemaName;
	}
	
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	
	public void setMovieTime(int movieTime) {
		this.movieTime = movieTime;
	}
	
	public void setTicketPrice(int ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	public void seturl(String url) {
		this.url = url;
	}
	
}
