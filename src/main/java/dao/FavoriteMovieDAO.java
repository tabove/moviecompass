package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.data.FavoriteMovie;

public class FavoriteMovieDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
    private final String USER = "postgres";
    private final String PASSWORD = "test";
    
    /*
     * コンストラクタでJDBCドライバの準備
     */
    public FavoriteMovieDAO() {
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * FavoriteMovieテーブルからString user_idとユーザIDが一致するもの、かつ
     * String movie_idと作品IDが一致するものを取り出して、
     * そのレコードのデータをお気に入り作品インスタンスに格納して、返す。
     * 
     * 引数：	String user_id:
     * 				ユーザIDと一致するかの検索ID
     * 			String movie_id:
     * 				作品IDと一致するかの検索ID
     * 
     * 戻り値：	FavoriteMovie:
     * 				１レコード分のデータが格納されているお気に入り作品インスタンス
     * 			null:
     * 				一致するレコードを見つけられなかった場合
     */
    public FavoriteMovie selectFavoriteMovieByUserIdAndMovieId(String user_id, String movie_id) {
    	/* 1) SQL文の準備 */
		String sql = "SELECT fm.favorite_movie_id, m.movie_id, m.movie_name, fm.torokubi ";
		sql += "FROM FavoriteMovie fm ";
		sql += "JOIN Movie m ON fm.movie_id = m.movie_id ";
		sql += "WHERE user_id = ? AND m.movie_id = ?;";
		
		// お気に入り作品インスタンスの準備
		FavoriteMovie favoriteMovie = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));
 			st.setInt(2, Integer.parseInt(movie_id));

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をお気に入り作品インスタンスに格納する */
			if (rs.next()) {
				favoriteMovie = makeFavoriteMovie(rs);
			}

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

 		return favoriteMovie;
    }
    
    /*
     * FavoriteMovieテーブルからString user_idとユーザIDが一致するものを全て取り出して、
     * そのレコードのデータをお気に入り作品インスタンスを生成時に格納する。生成した
     * お気に入り作品インスタンスをリストに追加して返す。
     * 
     * 引数：	String user_id:
     * 				ユーザIDと一致するかの検索ID
     * 
     * 戻り値：	List<FavoriteMovie>:
     * 				１レコード分のデータが格納されているお気に入り作品インスタンスの
     * 				ArrayList型のリスト
     * 			null:
     * 				ユーザIDと一致するレコードを見つけられなかった場合
     */
    public List<FavoriteMovie> selectFavoriteMovieByUserId(String user_id) {
    	/* 1) SQL文の準備 */
		String sql = "SELECT fm.favorite_movie_id, m.movie_id, m.movie_name, fm.torokubi ";
		sql += "FROM FavoriteMovie fm ";
		sql += "JOIN Movie m ON fm.movie_id = m.movie_id ";
		sql += "WHERE user_id = ?;";
		
		// お気に入り作品リストの準備
		List<FavoriteMovie> favoriteMovieList = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をお気に入り映画館リストに格納する */
			favoriteMovieList = makeFavoriteMovieList(rs);

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

 		return favoriteMovieList;
    }
    
	/*
     * FavoriteMovieテーブルへString user_idとString movie_idをペアとしてお気に入り
     * 登録を行う
     * 
     * 引数：	String user_id:
     * 				お気に入り登録を行うユーザID
     * 			String movie_id:
     * 				お気に入り登録を行う作品ID
     * 
     * 戻り値:	int:
     * 				レコードの挿入に成功した数
     */
    public int addFavoriteMovie(String user_id, String movie_id) {
    	int insertCount = 0;	// 更新件数

    	/* 1) SQL文の準備 */
		String sql = "INSERT INTO FavoriteMovie (user_id, movie_id, torokubi) ";
		sql += "VALUES (?, ?, ?);";
    	
    	/* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));
 			st.setInt(2, Integer.parseInt(movie_id));
 			st.setDate(3, new java.sql.Date(new Date().getTime()));

			/* 4) SQL文の実行 */
			insertCount = st.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
 		
    	return insertCount;
    }
    
	/*
     * FavoriteMovieテーブルから１件お気に入り登録されている
     * 作品を削除する
     * 
     * 引数：	String user_id:
     * 				ユーザIDと一致するかの検索ID
     * 			String movie_id:
     * 				作品IDと一致するかの検索ID
     * 
     * 戻り値:	int:
     * 				レコードの削除に成功した数
     */
    public int deleteFavoriteMovie(String user_id, String movie_id) {
    	int deleteCount = 0;	// 削除件数

    	/* 1) SQL文の準備 */
		String sql = "DELETE FROM FavoriteMovie ";
		sql += "WHERE user_id = ? AND movie_id = ?;";
    	
    	/* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));
 			st.setInt(2, Integer.parseInt(movie_id));

			/* 4) SQL文の実行 */
			deleteCount = st.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
 		
    	return deleteCount;
    }
    
    /*
     * ResultSet rsのデータをお気に入り作品インスタンスに格納したのち、
     * Listへ追加する。
     * 
     * 引数：	ResultSet rs:
     * 				FavoriteMovieテーブルに対して行った検索結果
     * 
     * 戻り値：	List<FavoriteMovie>:
     * 				１レコード分のデータが格納されているお気に入り作品インスタンスの
     * 				ArrayList型のリスト
     */
    private List<FavoriteMovie> makeFavoriteMovieList(ResultSet rs) throws Exception {
    	// リストの準備
    	List<FavoriteMovie> favoriteMovieList = new ArrayList<FavoriteMovie>();
    	
    	while (rs.next()) {
    		FavoriteMovie favoriteMovie = makeFavoriteMovie(rs);
    		
    		favoriteMovieList.add(favoriteMovie);
    	}
    	
    	return favoriteMovieList;
    }
    
    /*
     * ResultSet rsのデータをお気に入り作品インスタンスに格納する
     * 
     * 引数：	ResultSet rs:
     * 				FavoriteMovieテーブルに対して行った検索結果
     * 
     * 戻り値：	FavoriteMovie:
     * 				１レコード分のデータが格納されているお気に入り作品インスタンス
     */
    private FavoriteMovie makeFavoriteMovie(ResultSet rs) throws Exception {
    	// ResultSetの内容の読み込み
		String favorite_movie_id = rs.getString("favorite_movie_id");
		String movie_id = rs.getString("movie_id");
		String movie_name = rs.getString("movie_name");
		String torokubi = rs.getString("torokubi");
		
		// お気に入り作品データを格納するお気に入り作品インスタンスの生成
    	return new FavoriteMovie(favorite_movie_id, movie_id, movie_name, torokubi);
    }


}
