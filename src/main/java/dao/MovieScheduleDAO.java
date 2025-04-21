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

import model.data.MovieSchedule;
import model.data.TheaterSearch;

public class MovieScheduleDAO {
	
	//データベースに使用する情報
	private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
    private final String USER = "postgres";
    private final String PASSWORD = "test";
    
    //コンストラクタ
    public MovieScheduleDAO(){
    	try {
    		Class.forName("org.postgresql.Driver");
    	}catch(ClassNotFoundException e) {
    		e.printStackTrace();
    	}
    }
    
    //作品名の部分一致検索をしてくれるメソッド君
    public List<MovieSchedule> searchMovie(String cinema_id,String movie_name, String cinema_name, String movie_id, String genre, String date, String dateTime) {
    	List<MovieSchedule> movieList = new ArrayList<>();
        
    	 // デバッグ出力：受け取ったパラメータの確認
        System.out.println("DAOが受け取ったパラメータ: movie_name=" + movie_name + 
                          ", cinema_id=" + cinema_id + 
                          ", genre=" + genre + 
                          ", date=" + date + 
                          ", dateTime=" + dateTime);
    	
        StringBuilder sql = new StringBuilder(
            "SELECT c.cinema_name AS cinema_name, c.cinema_id AS cinema_id, m.movie_name AS movie_name, m.movie_id AS movie_id, " +
            "ms.movie_time AS movie_time, ms.ticket_price AS ticket_price, m.movie_genre AS genre " +
            "FROM movieschedule ms " +
            "JOIN cinema c ON ms.cinema_id = c.cinema_id " +
            "JOIN movie m ON ms.movie_id = m.movie_id " +
            "WHERE 1=1 " 
        );
        
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        
        //選択された映画感IDを使用した検索条件
        if (cinema_id != null && !cinema_id.isEmpty()) {
            conditions.add("c.cinema_id = ?");
            try {
                params.add(Integer.parseInt(cinema_id));
            } catch (NumberFormatException e) {
                System.out.println("無効なcinema_id: " + cinema_id);
            }
        }
        
        // 映画名の条件
        if (movie_name != null && !movie_name.isEmpty()) {
        	System.out.println("映画名条件を追加: " + movie_name);
            conditions.add("LOWER(m.movie_name) LIKE LOWER(?)");
            params.add("%" + movie_name + "%");
        }
        
        // 映画idの条件
        if (movie_id != null && !movie_id.isEmpty()) {
            conditions.add("LOWER(m.movie_id) LIKE LOWER(?)");
            params.add("%" + movie_id + "%");
        }

        // ジャンルの条件
        if (genre != null && !genre.isEmpty()) {
            conditions.add("LOWER(m.movie_genre) LIKE LOWER(?)");
            params.add("%" + genre + "%");
        }

        // 日付の条件
        if (date != null && !date.isEmpty()) {
            conditions.add("DATE(ms.movie_time) >= ?::date");
            params.add(date);
        }

        // 時間の条件
        if (dateTime != null && !dateTime.isEmpty()) {
            conditions.add("TIME(ms.movie_time) >= ?::dateTime");
            params.add(dateTime);
        }
        
        // conditionの中身チェックからのSQL文に追加
        if (!conditions.isEmpty()) {
            sql.append(" AND " + String.join(" AND ", conditions));
        }
        
        sql.append(" ORDER BY c.cinema_name, m.movie_name, ms.movie_time ASC ");
        
//        //デバッグ出力
//		System.out.println("実行するSQL: " + sql);
//		System.out.println("バインドパラメータ: " + params);
//		System.out.println("バインドパラメータ: " + movie_id);

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(sql.toString())) {

        	// パラメータをセット
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
        	
        	 // デバッグコード
            //System.out.println("バインドパラメータ: " + "%" + movie_name + "%");
            ResultSet rs = st.executeQuery();
            
            //結果の差し替え
    		movieList = makeMovieList(rs);

    		
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("クエリエラー: " + e.getMessage());
        }
        return movieList;
    }

    
    //重要！！後で移動！！！
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
    
    public List<MovieSchedule> makeMovieList(ResultSet rs)throws Exception{
	
	//全検索結果を格納するリストを準備
    	List<MovieSchedule> makeMovieList = new ArrayList<MovieSchedule>();
	
    	while(rs.next()) {
			String cinema_name = rs.getString("cinema_name");
			String cinema_id = rs.getString("cinema_id");
			String movie_id = rs.getString("movie_id");
			String movie_name = rs.getString("movie_name");
			String movie_time = rs.getString("movie_time");
			int ticket_price = rs.getInt("ticket_price");
			
			// 日付と時間を分割
		    String[] splitDateTime = movie_time.split(" ");
		    String movie_date = splitDateTime[0];  // 日付
		    String movie_hour = splitDateTime[1];  // 日時
			
			//1行分のデータを格納するインスタンス
			MovieSchedule search = new MovieSchedule(cinema_name,
													cinema_id,
													movie_name,
													movie_id,
													movie_time,
													movie_date,
													movie_hour,
													ticket_price);
			// リストに1行分のデータを追加する
			makeMovieList.add(search);
		}
		return makeMovieList;
	}

}
