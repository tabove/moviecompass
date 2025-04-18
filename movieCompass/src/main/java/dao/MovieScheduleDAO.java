package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public List<MovieSchedule> searchMovie(String movie_name, String cinema_name, String genre, String date, String dateTime) {
    	List<MovieSchedule> movieList = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder(
            "SELECT c.cinema_name AS cinema_name, m.movie_name AS movie_name, " +
            "ms.movie_time AS movie_time, ms.ticket_price AS ticket_price, m.movie_genre AS genre " +
            "FROM movieschedule ms " +
            "JOIN cinema c ON ms.cinema_id = c.cinema_id " +
            "JOIN movie m ON ms.movie_id = m.movie_id " +
            "WHERE 1=1 " +
            "AND LOWER(m.movie_name) LIKE LOWER(?) " +
            "ORDER BY c.cinema_name, m.movie_name, ms.movie_time ASC "
        );

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(sql.toString())) {

        	st.setString(1,"%"+movie_name+"%");
        	
        	 // デバッグコード
//            System.out.println("バインドパラメータ: " + "%" + movieName + "%");
            ResultSet rs = st.executeQuery();
            
            //結果の差し替え
    		movieList = makeMovieList(rs);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("クエリエラー: " + e.getMessage());
        }
        return movieList;
    }

    
    //ジャンル検索の欄に、現在登録してあるジャンルを抽出するメソッド君    
    public List<String> genreList(){
    	List<String> genreList = new ArrayList<>();
    	
    	String sql = "SELECT DISTINCT movie_genre " +
    				 "FROM Movie ; ";
    	
    	try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement st = con.prepareStatement(sql)) {
    		
    		ResultSet rs = st.executeQuery();
    		while (rs.next()) {
    			
				String genres = rs.getString("movie_genre");
				
				//カンマ区切りでリストに追加
				String[] splitGenres = genres.split(",");
				
				for( String genre : splitGenres) {
					genreList.add(genre.trim());
				}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genreList;
    }
    
    //映画館を抽出するメソッド    
    public List<TheaterSearch> theaterList(){
    	List<TheaterSearch> theaterList = new ArrayList<>();
    	
    	String sql = "SELECT cinema_name " +
    				 "FROM Cinema ; ";
    	
    	try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement st = con.prepareStatement(sql)) {
    		
    		ResultSet rs = st.executeQuery();
    		
    		while (rs.next()) {
    			
				String cinema_name = rs.getString("cinema_name");
				TheaterSearch cName = new TheaterSearch(cinema_name);
				
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
			String movie_name = rs.getString("movie_name");
			String movie_time = rs.getString("movie_time");
			int ticket_price = rs.getInt("ticket_price");
			
			// 日付と時間を分割
		    String[] splitDateTime = movie_time.split(" ");
		    String movie_date = splitDateTime[0];  // 日付
		    String movie_hour = splitDateTime[1];  // 日時
			
			//1行分のデータを格納するインスタンス
			MovieSchedule search = new MovieSchedule(cinema_name,
													movie_name,
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
