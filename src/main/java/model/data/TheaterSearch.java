package model.data;

public class TheaterSearch {
	
	private String cinema_id;
	private String cinema_name;

    public TheaterSearch() {}

    public TheaterSearch(String cinema_id,String cinema_name) {
    	this.cinema_id = cinema_id;
    	this.cinema_name = cinema_name;
    }

	public String getCinema_id() {
		return cinema_id;
	}

	public String getCinema_name() {
		return cinema_name;
	}

}
