package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.data.Reservation;

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
	
	public boolean create(Reservation reservation) {
		// SQL文の準備
		String sql = "INSERT INTO Reservation(user_id,"
				+ "cinema_id, movie_id, movie_time, ticket_price, torokubi)"
				+ "VALUES(?, ?, ?, ?, ?, ?);";
		
		// PostgreSQLへの接続
		try(Connection con = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pSmt = con.prepareStatement(sql);){
			
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date d = sf.parse(reservation.getMovie_time());
			Timestamp t = new Timestamp(d.getTime());
			
			// SQL文の?部分の置き換え
//			pSmt.setString(1, reservation.getReservation_id());
			pSmt.setInt(1, Integer.parseInt(reservation.getUser_id()));
			pSmt.setInt(2, Integer.parseInt(reservation.getCinema_id()));
			pSmt.setInt(3, Integer.parseInt(reservation.getMovie_id()));
			pSmt.setTimestamp(4, t);
			pSmt.setInt(5, reservation.getTicket_price());
			pSmt.setDate(6, new java.sql.Date(new Date().getTime()));
			
			// SQL文の実行
			int result =pSmt.executeUpdate();
			if(result != 1) { // 追加に失敗した時（追加件数が1件じゃない時）
				return false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
