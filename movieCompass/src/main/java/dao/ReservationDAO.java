package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.data.Reservation;
import model.data.SearchCondition;

public class ReservationDAO {
	//データベース接続に使用する情報
	private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
	private final String USER = "postgres";
	private final String PASS = "test";
	
	public ReservationDAO() {
		// JDBCドライバの準備
		try {
			Class.forName("org.postgresql.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public List<Reservation> f1() throws Exception{
		List<Reservation> reserveList = new ArrayList<>();
		int ticketPrice = 0;
		
		// 上映時刻の取り出しと変換
		SearchCondition SC = new SearchCondition();
		String time = String.valueOf(SC.getMovieTime());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date d = sf.parse(time);
		Timestamp t = new Timestamp(d.getTime());
		
		// SQL文の準備（INSERT文）
		String sql = "INSERT INTO Reservation (reservation_id, user_id, cinema_id, movie_id, movie_time, ticket_price, torokubi)"
				+ "VALUES ()";
		
		// PostgreSQLへの接続
		try(Connection con = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pSmt = con.prepareStatement(sql);){
			// SELECT文の実行
			ResultSet rs = pSmt.executeQuery(); //update
			// 結果をリストに移し替える
			while(rs.next()) {
				String reservationId = rs.getString("reservationId");
				String userId = rs.getString("userId");
				String cinemaId = rs.getString("cinemaId");
				String movieId = rs.getString("movieId");
				String movieTime = rs.getString("movieTime");
				ticketPrice = rs.getInt(ticketPrice);
				String torokubi = rs.getString("torokubi");
				
				Reservation reservation = new Reservation(reservationId,
														userId,
														cinemaId,
														movieId,
														movieTime,
														ticketPrice,
														torokubi);
				reserveList.add(reservation);
			}
		} catch(Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
		return reserveList;
	}

}
