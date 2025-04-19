package model.data;

public class TheaterSearch {
    private String cinema_name;

    public TheaterSearch() {}

    public TheaterSearch(String cinema_name) {
        this.cinema_name = cinema_name;
    }

    public String getCinema_name() {
        return cinema_name;
    }

    public void setCinema_name(String cinema_name) {
        this.cinema_name = cinema_name;
    }
}
