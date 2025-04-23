package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public int userList(User user) {
    	int count= 0;		// 更新件数
    	
    	/* 1) SQL文の準備 */
    	String sql = "INSERT INTO \"User\"(user_mail_address,user_pass,user_name,torokubi)VALUES(?,?,?,?);";
		
		
		/* 2) PostgreSQLへの接続 */
		try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
				PreparedStatement st = con.prepareStatement(sql);) {
			
			// 「?」 の値を設定する。。。
			st.setString(1,user.getMail());
			st.setString(2,user.getPass());
			st.setString(3,user.getName());
			st.setDate(4, new java.sql.Date(new Date().getTime()));
			
			
			/* 3) SQL文の実行 */
			count = st.executeUpdate();//【errが出るから適当に変数名を変えて。。。】
			
		} catch (Exception e) {
			System.out.println("DBアクセス時にエラーが発生しました。");
			e.printStackTrace();
		}
		return count;
    }

    //ユーザ登録があるかを検索(完全一致)
	 public User userSearch(String mail) {
		 User loginUser = null;
		 
		 String sql = "SELECT * ";
		 
		 sql += "FROM \"User\" ";
		 sql += "WHERE user_mail_address = ? ";
		 
		 try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
					PreparedStatement st = con.prepareStatement(sql);) {
	
			 st.setString(1, mail);
			 
//			System.out.println(st);
			 
			 ResultSet rs = st.executeQuery();
			 loginUser = makeUserList(rs);
			 
		 } catch (Exception e) {
			 System.out.println("DBアクセス時にエラーが発生しました。");
			 e.printStackTrace();
		 }
	
		 return loginUser;
	 }
  	 
  	 
  	 public User makeUserList(ResultSet rs) throws Exception {

			// 全検索結果を格納するリストを準備
			User user = null;
	
			while (rs.next()) {
				// 1行分のデータを読込む
				String id = rs.getString("user_id");
				String mail = rs.getString("user_mail_address");
				String pass = rs.getString("user_pass");
				String name = rs.getString("user_name");
				String torokubi = rs.getString("torokubi");
	
				// 1行分のデータを格納するインスタンス
				user = new User(id, mail, pass, name, torokubi);
											
			}
			return user;
	 }
  	 
}
