package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.data.Cinema;
import model.data.TheaterSearch;

public class CinemaDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
    private final String USER = "postgres";
    private final String PASSWORD = "test";
    
    /*
     * コンストラクタでJDBCドライバの準備
     */
    public CinemaDAO() {
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * CinemaテーブルからString cinema_idと映画館IDが一致するものを取り出して、
     * そのレコードのデータを映画館インスタンスを生成時に格納する。
     * 最後にその映画館インスタンスを返す。
     * 
     * 引数：	String cinema_id:
     * 				映画館IDと一致するかの検索ID
     * 
     * 戻り値：	Cinema:
     * 				１レコード分のデータが格納されている映画館インスタンス
     */
    public Cinema selectCinemaById(String cinema_id) {
    	/* 1) SQL文の準備 */
		String sql = "SELECT * FROM Cinema ";
		sql += "WHERE cinema_id = ?;";
		
		// 映画館インスタンスの準備
		Cinema cinema = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(cinema_id));

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果を映画館インスタンスに格納する */
			if (rs.next()) {
				cinema = makeCinema(rs);
			}

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

 		return cinema;
    }
    
    //映画館を抽出するメソッド    
	public List<TheaterSearch> theaterList(){
		List<TheaterSearch> theaterList = new ArrayList<>();
		
		String sql = "SELECT cinema_id, cinema_name " +
					 "FROM Cinema ; ";
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
	            PreparedStatement st = con.prepareStatement(sql)) {
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				String cinema_id = rs.getString("cinema_id");
				String cinema_name = rs.getString("cinema_name");
				TheaterSearch cName = new TheaterSearch(cinema_id,cinema_name);
				
				// リストに1行分のデータを追加する
				theaterList.add(cName);
	        }
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return theaterList;
	}

	/*
     * ResultSet rsのデータを映画館インスタンスに格納する
     * 
     * 引数：	ResultSet rs:
     * 				Cinemaテーブルに対して行った検索結果
     * 
     * 戻り値：	Cinema:
     * 				１レコード分のデータが格納されている映画館インスタンス
     */
    private Cinema makeCinema(ResultSet rs) throws Exception {
    	// ResultSetの内容の読み込み
		String cinema_id = rs.getString("cinema_id");
		String cinema_name = rs.getString("cinema_name");
		String address = rs.getString("address");
		String phone_number = rs.getString("phone_number");
		
		// 映画館データを格納する映画館インスタンスの生成
    	return new Cinema(cinema_id, cinema_name, address, phone_number);
    }
}
