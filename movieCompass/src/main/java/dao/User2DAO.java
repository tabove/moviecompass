package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;

import model.data.User;

public class UserDAO {
	// DB接続
    private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
    private final String USER = "postgres";
    private final String PASSWORD = "test";

	// コンストラクタ
	public UserDAO() {
        /* JDBCドライバの準備 */
    	try {
    		Class.forName("org.postgresql.Driver");
    	} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

  		
  		// 入力情報の結果を格納するリストを準備
  	  		/* 1) SQL文の準備 */
  		 /*
  	     * 1件データを登録
  	     */
  	    public int userList(User user) {
  	        int count= 0;		// 更新件数
  		
  		
  		String sql = "INSERT INTO \"User\"(user_mail_address,user_pass,user_name,torokubi)VALUES(?,?,?,?);";
  		
  		
  		/* 2) PostgreSQLへの接続 */
  		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
  				PreparedStatement st = con.prepareStatement(sql);) {
  			// 「?」 の値を設定する。。。
  			
  			st.setString(1,user.getMail());
  			st.setString(2,user.getPass());
  			st.setString(3,user.getName());
  			st.setDate(4, new java.sql.Date(new Date().getTime()));
  			
  			
  			System.out.println(st);
  			
  			/* 3) SQL文の実行 */
  			count = st.executeUpdate();//【errが出るから適当に変数名を変えて。。。】
  			
  			
  		} catch (Exception e) {
  			System.out.println("DBアクセス時にエラーが発生しました。");
  			e.printStackTrace();
  		}
  		return count;
  	}
	   	

 

}
