package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.data.Movie;

public class MovieDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
    private final String USER = "postgres";
    private final String PASSWORD = "test";
    
    /*
     * コンストラクタでJDBCドライバの準備
     */
    public MovieDAO() {
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * MovieテーブルからString movie_idと作品IDが一致するものを取り出して、
     * そのレコードのデータを作品インスタンスを生成時に格納する。
     * 最後にその作品インスタンスを返す。
     * 
     * 引数：	String movie_id:
     * 				作品IDと一致するかの検索ID
     * 
     * 戻り値：	Movie:
     * 				１レコード分のデータが格納されている作品インスタンス
     */
    public Movie selectMovieById(String movie_id) {
    	/* 1) SQL文の準備 */
		String sql = "SELECT * FROM Movie ";
		sql += "WHERE movie_id = ?;";
		
		// 作品インスタンスの準備
		Movie movie = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(movie_id));

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果を映画館インスタンスに格納する */
			if (rs.next()) {
				movie = makeMovie(rs);
			}

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

 		return movie;
    }
    
    //ジャンル検索の欄に、現在登録してあるジャンルを抽出するメソッド君    
	public List<String> genreList(){
		Set<String> uniqueGenres = new HashSet<>(); // 重複削除のためのセット
		
		String sql = "SELECT movie_genre " +
					 "FROM Movie ; ";
		
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
	            PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {
			
			while (rs.next()) {
				
				String genres = rs.getString("movie_genre");
				
				// ジャンルを分割＆重複を防ぐためにセットへ追加
	            String[] splitGenres = genres.split(",");
	            for (String genre : splitGenres) {
	                uniqueGenres.add(genre.trim()); // 空白を削除してセットに追加
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		 return new ArrayList<>(uniqueGenres); // Setをリスト化して返す
	}

	/*
     * ResultSet rsのデータを作品インスタンスに格納する
     * 
     * 引数：	ResultSet rs:
     * 				Movieテーブルに対して行った検索結果
     * 
     * 戻り値：	Movie:
     * 				１レコード分のデータが格納されている作品インスタンス
     */
    private Movie makeMovie(ResultSet rs) throws Exception {
    	// ResultSetの内容の読み込み
		String movie_id = rs.getString("movie_id");
		String movie_name = rs.getString("movie_name");
		String movie_genre = rs.getString("movie_genre");
		String movie_description = rs.getString("movie_description");
		String movie_release_date = rs.getString("movie_release_date");
		int movie_duration = rs.getInt("movie_duration");
		
		// 作品データを格納する作品インスタンスの生成
    	return new Movie(movie_id, movie_name, movie_genre, 
    					movie_description, movie_release_date, movie_duration);
    }
    
}
