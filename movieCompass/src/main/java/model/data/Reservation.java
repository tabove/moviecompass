package model.data;
// JavaBeans

import java.io.Serializable;

public class Reservation implements Serializable {
	private String reservation_id; // 予約ID
	private String user_id; // ユーザID
	private String cinema_id; // 映画館ID
	private String movie_id; // 作品ID
	private String cinema_name; // 映画館名
	private String movie_name; // 作品名
	private String movie_time; // 上映日時
	private int ticket_price; // チケット単価
	private String torokubi; // 登録日
	
	public Reservation() {}
	
	public Reservation(String reservation_id, String user_id, String cinema_id, String movie_id,
			String cinema_name, String movie_name, String movie_time, int ticket_price, String torokubi){
		this.reservation_id = reservation_id;
		this.user_id = user_id;
		this.cinema_id = cinema_id;
		this.movie_id = movie_id;
		this.cinema_name = cinema_name;
		this.movie_name = movie_name;
		this.movie_time = movie_time;
		this.ticket_price = ticket_price;
		this.torokubi = torokubi;
		// 
	}
	public String getReservation_id() {
		return reservation_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getCinema_id() {
		return cinema_id;
	}

	public String getMovie_id() {
		return movie_id;
	}

	public String getCinema_name() {
		return cinema_name;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public String getMovie_time() {
		return movie_time;
	}

	public int getTicket_price() {
		return ticket_price;
	}

	public String getTorokubi() {
		return torokubi;
	}

}
