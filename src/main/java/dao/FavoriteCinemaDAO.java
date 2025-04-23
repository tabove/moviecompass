package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.data.FavoriteCinema;

public class FavoriteCinemaDAO {
	private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
    private final String USER = "postgres";
    private final String PASSWORD = "test";
    
    /*
     * コンストラクタでJDBCドライバの準備
     */
    public FavoriteCinemaDAO() {
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    /*
     * FavoriteCinemaテーブルからString user_idとユーザIDが一致するもの、かつ
     * String cinema_idと映画館IDが一致するものを取り出して、
     * そのレコードのデータをお気に入り映画館インスタンスに格納して、返す。
     * 
     * 引数：	String user_id:
     * 				ユーザIDと一致するかの検索ID
     * 			String cinema_id:
     * 				映画館IDと一致するかの検索ID
     * 
     * 戻り値：	FavoriteCinema:
     * 				１レコード分のデータが格納されているお気に入り映画館インスタンス
     * 			null:
     * 				一致するレコードを見つけられなかった場合
     */
    public FavoriteCinema selectFavoriteCinemaByUserIdAndCinemaId(String user_id, String cinema_id) {
    	/* 1) SQL文の準備 */
		String sql = "SELECT fc.favorite_cinema_id, c.cinema_id, c.cinema_name, fc.torokubi ";
		sql += "FROM FavoriteCinema fc ";
		sql += "JOIN Cinema c ON fc.cinema_id = c.cinema_id ";
		sql += "WHERE user_id = ? AND c.cinema_id = ?;";
		
		// お気に入り映画館インスタンスの準備
		FavoriteCinema favoriteCinema = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));
 			st.setInt(2, Integer.parseInt(cinema_id));

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をお気に入り映画館インスタンスに格納する */
			if (rs.next()) {
				favoriteCinema = makeFavoriteCinema(rs);
			}

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

 		return favoriteCinema;
    }
    
    /*
     * FavoriteCinemaテーブルからString user_idとユーザIDが一致するものを全て取り出して、
     * そのレコードのデータをお気に入り映画館インスタンスを生成時に格納する。生成した
     * お気に入り映画館インスタンスをリストに追加して返す。順番は映画館名の昇順。
     * 
     * 引数：	String user_id:
     * 				ユーザIDと一致するかの検索ID
     * 
     * 戻り値：	List<FavoriteCinema>:
     * 				１レコード分のデータが格納されているお気に入り映画館インスタンスの
     * 				ArrayList型のリスト
     * 			null:
     * 				ユーザIDと一致するレコードを見つけられなかった場合
     */
    public List<FavoriteCinema> selectFavoriteCinemaByUserId(String user_id) {
    	/* 1) SQL文の準備 */
		String sql = "SELECT fc.favorite_cinema_id, c.cinema_id, c.cinema_name, fc.torokubi ";
		sql += "FROM FavoriteCinema fc ";
		sql += "JOIN Cinema c ON fc.cinema_id = c.cinema_id ";
		sql += "WHERE user_id = ? ";
		sql += "ORDER BY c.cinema_name;";
		
		// お気に入り映画館リストの準備
		List<FavoriteCinema> favoriteCinemaList = null;

        /* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));

			/* 4) SQL文の実行 */
			ResultSet rs = st.executeQuery();

			/* 5) 結果をお気に入り映画館リストに格納する */
			favoriteCinemaList = makeFavoriteCinemaList(rs);

		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}

 		return favoriteCinemaList;
    }
    
	/*
     * FavoriteCinemaテーブルへString user_idとString cinema_idをペアとしてお気に入り
     * 登録を行う
     * 
     * 引数：	String user_id:
     * 				お気に入り登録を行うユーザID
     * 			String cinema_id:
     * 				お気に入り登録を行う映画館ID
     * 
     * 戻り値:	int:
     * 				レコードの挿入に成功した数
     */
    public int addFavoriteCinema(String user_id, String cinema_id) {
    	int insertCount = 0;	// 更新件数

    	/* 1) SQL文の準備 */
		String sql = "INSERT INTO FavoriteCinema (user_id, cinema_id, torokubi) ";
		sql += "VALUES (?, ?, ?);";
    	
    	/* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));
 			st.setInt(2, Integer.parseInt(cinema_id));
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
     * FavoriteCinemaテーブルから１件お気に入り登録されている
     * 映画館を削除する
     * 
     * 引数：	String user_id:
     * 				ユーザIDと一致するかの検索ID
     * 			String cinema_id:
     * 				映画館IDと一致するかの検索ID
     * 
     * 戻り値:	int:
     * 				レコードの削除に成功した数
     */
    public int deleteFavoriteCinema(String user_id, String cinema_id) {
    	int deleteCount = 0;	// 削除件数

    	/* 1) SQL文の準備 */
		String sql = "DELETE FROM FavoriteCinema ";
		sql += "WHERE user_id = ? AND cinema_id = ?;";
    	
    	/* 2) PostgreSQLへの接続 */
 		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
 			PreparedStatement st = con.prepareStatement(sql);) {
 			
 			/* 3) SQL文の?部分を置き換え */
 			st.setInt(1, Integer.parseInt(user_id));
 			st.setInt(2, Integer.parseInt(cinema_id));

			/* 4) SQL文の実行 */
			deleteCount = st.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
 		
    	return deleteCount;
    }
    
    /*
     * ResultSet rsのデータをお気に入り映画館インスタンスに格納したのち、
     * Listへ追加する。
     * 
     * 引数：	ResultSet rs:
     * 				FavoriteCinemaテーブルに対して行った検索結果
     * 
     * 戻り値：	List<FavoriteCinema>:
     * 				１レコード分のデータが格納されているお気に入り映画館インスタンスの
     * 				ArrayList型のリスト
     */
    private List<FavoriteCinema> makeFavoriteCinemaList(ResultSet rs) throws Exception {
    	// リストの準備
    	List<FavoriteCinema> favoriteCinemaList = new ArrayList<FavoriteCinema>();
    	
    	while (rs.next()) {
    		FavoriteCinema favoriteCinema = makeFavoriteCinema(rs);
    		
    		favoriteCinemaList.add(favoriteCinema);
    	}
    	
    	return favoriteCinemaList;
    }
    
    /*
     * ResultSet rsのデータをお気に入り映画館インスタンスに格納する
     * 
     * 引数：	ResultSet rs:
     * 				FavoriteCinemaテーブルに対して行った検索結果
     * 
     * 戻り値：	FavoriteCinema:
     * 				１レコード分のデータが格納されているお気に入り映画館インスタンス
     */
    private FavoriteCinema makeFavoriteCinema(ResultSet rs) throws Exception {
    	// ResultSetの内容の読み込み
		String favorite_cinema_id = rs.getString("favorite_cinema_id");
		String cinema_id = rs.getString("cinema_id");
		String cinema_name = rs.getString("cinema_name");
		String torokubi = rs.getString("torokubi");
		
		// お気に入り映画館データを格納するお気に入り映画館インスタンスの生成
    	return new FavoriteCinema(favorite_cinema_id, cinema_id, cinema_name, torokubi);
    }

}
