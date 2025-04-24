package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.data.MovieSchedule;

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
        
    	///* // デバッグ出力：受け取ったパラメータの確認
        System.out.println("DAOが受け取ったパラメータ: movie_name=" + movie_name + 
                          ", cinema_id=" + cinema_id + 
                          ", genre=" + genre + 
                          ", date=" + date + 
                          ", dateTime=" + dateTime); 
        //*/
    	
        StringBuilder sql = new StringBuilder(
            "SELECT c.cinema_name AS cinema_name, c.cinema_id AS cinema_id, m.movie_name AS movie_name, m.movie_id AS movie_id, " +
            "ms.movie_time AS movie_time, ms.ticket_price AS ticket_price, m.movie_genre AS genre " +
            "FROM movieschedule ms " +
            "JOIN cinema c ON ms.cinema_id = c.cinema_id " +
            "JOIN movie m ON ms.movie_id = m.movie_id "
        );
        
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        
        //選択された映画館IDを使用した検索条件
        if (cinema_id != null && !cinema_id.isEmpty()) {
        	System.out.println(cinema_id);
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
        
        //選択された作品IDを使用した検索条件
        if (movie_id != null && !movie_id.isEmpty()) {
        	System.out.println("作品ID条件を追加：" + movie_id);
            conditions.add("m.movie_id = ?");
            try {
                params.add(Integer.parseInt(movie_id));
            } catch (NumberFormatException e) {
                System.out.println("無効なmovie_id: " + movie_id);
            }
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

        // 時間の条件 - 修正版
        if (dateTime != null && !dateTime.isEmpty()) {
            // 当日の指定時間以降の映画を検索する場合
            if (date != null && !date.isEmpty()) {
                // 日付と時間の両方が指定されている場合、その日の指定時間以降
                conditions.add("ms.movie_time >= ?::timestamp");
                params.add(date + " " + dateTime + ":00");
            } else {
                // 日付指定がない場合、すべての日付で指定時間以降
                conditions.add("CAST(ms.movie_time AS time) >= ?::time");
                params.add(dateTime + ":00");
            }
        }
        
        // conditionの中身チェックからのSQL文に追加
        if (!conditions.isEmpty()) {
            sql.append( "WHERE movie_time >= CURRENT_TIMESTAMP" + " AND " + String.join(" AND ", conditions));
        } else {
        	sql.append( "WHERE 1=0 ");
        }
        
        sql.append(" ORDER BY c.cinema_name, m.movie_name, ms.movie_time ASC ");
        
        ///* //デバッグ出力
		System.out.println("実行するSQL: " + sql);
		System.out.println("バインドパラメータ: " + params);
		//*/

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(sql.toString())) {

        	// パラメータをセット
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            
            ///* //デバッグ出力
    		System.out.println("実行するPreparedStatement: " + st);
    		//*/
        	
            ResultSet rs = st.executeQuery();
            
            //結果の差し替え
    		movieList = makeMovieList(rs);

    		
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("クエリエラー: " + e.getMessage());
        }
        return movieList;
    }

    
    public List<MovieSchedule> makeMovieList(ResultSet rs)throws Exception{
	
	//全検索結果を格納するリストを準備
    	List<MovieSchedule> makeMovieList = new ArrayList<MovieSchedule>();
	
    	while(rs.next()) {
			String cinema_name = rs.getString("cinema_name");
			String cinema_id = rs.getString("cinema_id");
			String movie_name = rs.getString("movie_name");
			String movie_id = rs.getString("movie_id");
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
