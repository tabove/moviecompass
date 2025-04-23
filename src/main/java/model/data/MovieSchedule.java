package model.data;

public class MovieSchedule {
		private String cinema_id; // 映画館ID
		private String movie_id; // 作品ID
		private String cinema_name; // 映画館名
		private String movie_name; //作品名
		private String movie_time; // 上映日時
		private String movie_date; // 上映日(切り分け後)
		private String movie_hour; // 上映時間(切り分け後)
		private int ticket_price; // チケット単価
		private String torokubi; // 登録日
		
		public MovieSchedule() {}
		public MovieSchedule(String cinema_name,String cinema_id, String movie_name, String movie_id,
				String movie_time, String movie_date, String movie_hour, int ticket_price){
			this.cinema_id = cinema_id;
			this.cinema_name = cinema_name;
			this.movie_id = movie_id;
			this.movie_name = movie_name;
			this.movie_time = movie_time;
			this.movie_date = movie_date;
			this.movie_hour = movie_hour;
			this.ticket_price = ticket_price;
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
		public String getMovie_date() {
			return movie_date;
		}
		public String getMovie_hour() {
			return movie_hour;
		}
		public int getTicket_price() {
			return ticket_price;
		}
		public String getTorokubi() {
			return torokubi;
		}
		
}

