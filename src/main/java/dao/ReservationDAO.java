package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	/*
	 * 予約テーブルへ1件予約データを登録する
	 */
	public boolean create(String user_id, String cinema_id, String movie_id, 
						String movie_time, int repTicket_price) {
		// SQL文の準備
		String sql = "INSERT INTO Reservation(user_id,"
				+ "cinema_id, movie_id, movie_time, ticket_price, torokubi)"
				+ "VALUES(?, ?, ?, ?, ?, ?);";
		
		// PostgreSQLへの接続
		try(Connection con = DriverManager.getConnection(URL, USER, PASS);
				PreparedStatement pSmt = con.prepareStatement(sql);){
			
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date d = sf.parse(movie_time);
			Timestamp t = new Timestamp(d.getTime());
			
			// SQL文の?部分の置き換え
//			pSmt.setString(1, reservation.getReservation_id());
			pSmt.setInt(1, Integer.parseInt(user_id));
			pSmt.setInt(2, Integer.parseInt(cinema_id));
			pSmt.setInt(3, Integer.parseInt(movie_id));
			pSmt.setTimestamp(4, t);
			pSmt.setInt(5, repTicket_price);
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
	
	/*
     * ReservationテーブルからString user_idとユーザIDが一致するものを全て取り出して、
     * そのレコードのデータを予約インスタンスを生成時に格納する。生成した
     * 予約インスタンスをリストに追加して返す。
     * 
     * 引数：	String user_id:
     * 				ユーザIDと一致するかの検索ID
     * 
     * 戻り値：	List<Reservation>:
     * 				１レコード分のデータが格納されている予約インスタンスの
     * 				ArrayList型のリスト
     * 			null:
     * 				ユーザIDと一致するレコードを見つけられなかった場合
     */
	public List<Reservation> selectAll(String user_id){
		// 予約リストの準備
		List<Reservation> reservationList = null;
		
		/* 1) SQL文の準備 */
		String sql = "SELECT r.reservation_id, r.user_id, c.cinema_id, c.cinema_name, ";
		sql += "m.movie_id, m.movie_name, r.movie_time, r.ticket_price, r.torokubi ";
		sql += "FROM Reservation r ";
		sql += "JOIN Movie m ON r.movie_id = m.movie_id ";
		sql += "JOIN Cinema c ON r.cinema_id = c.cinema_id ";
		sql += "WHERE user_id = ? ";
		sql += "AND movie_time >= CURRENT_TIMESTAMP ";
		sql += "ORDER BY r.movie_time, c.cinema_name, m.movie_name;";

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASS);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をお気に入り映画館リストに格納する */
			reservationList = makeReservationList(rs);

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

 		return reservationList;
	}
	
    /*
     * ResultSet rsのデータを予約インスタンスに格納したのち、
     * Listへ追加する。
     * 
     * 引数：	ResultSet rs:
     * 				Reservationテーブルに対して行った検索結果
     * 
     * 戻り値：	List<Reservation>:
     * 				１レコード分のデータが格納されている予約インスタンスの
     * 				ArrayList型のリスト
     */
	private List<Reservation> makeReservationList(ResultSet rs) throws Exception {
		// リストの準備
		List<Reservation> reservationList = new ArrayList<Reservation>();
		
		while (rs.next()) {
			String reservation_id = rs.getString("reservation_id");
			String user_id = rs.getString("user_id");
			String cinema_id = rs.getString("cinema_id");
			String cinema_name = rs.getString("cinema_name");
			String movie_id = rs.getString("movie_id");
			String movie_name = rs.getString("movie_name");
			String movie_time = rs.getString("movie_time");
			int ticket_price = rs.getInt("ticket_price");
			String torokubi = rs.getString("torokubi");
			
			// Reservationインスタンスの生成
			Reservation reservation = new Reservation(reservation_id, user_id, cinema_id, movie_id, 
									cinema_name, movie_name, movie_time, ticket_price, torokubi);
    		
			// リストに追加
    		reservationList.add(reservation);
    	}
		
		return reservationList;
	}

}
