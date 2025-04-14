package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.SearchCondition;

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
    
    //作品名部分一致検索
    public List<SearchCondition> Title(String title) {
        
    	List<SearchCondition> titleList = new ArrayList<>();

        String sql = "SELECT Movie FROM movie_id WHERE 1=1" ;
        if (title != null && !title.isEmpty()) {
            sql += " AND movie_id LIKE ?" ;  // 作品名の検索
        }
        sql += " ORDER BY cinema_id ASC; " ;

        try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement st = con.prepareStatement(sql)) {

            int index = 1;
            if (title != null && !title.isEmpty()) {
            	 // 部分一致検索
                st.setString(index++, "%" + title + "%");
            }
            
            ResultSet rs = st.executeQuery();
            
            //結果の差し替え
    		titleList = makeTitleList(rs);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return titleList;
    }

    public List<SearchCondition> makeTitleList(ResultSet rs)throws Exception{
        	
        	//全検索結果を格納するリストを準備
    	List<SearchCondition> titleList = new ArrayList<SearchCondition>();
        	
        	while(rs.next()) {
        		String cinemaName = rs.getString("cinemaName");
        		String titleName = rs.getString("titleName");
    			int movieTime = rs.getInt("movieTime");
    			int ticketPrice = rs.getInt("ticketPice");
        	
    			//1行分のデータを格納するインスタンス
    			SearchCondition search = new SearchCondition(cinemaName,
    										titleName,
    										movieTime,
    										ticketPrice);
    			// リストに1行分のデータを追加する
    			titleList.add(search);
		}
    	return titleList;
    }
}
