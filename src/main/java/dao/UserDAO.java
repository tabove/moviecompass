package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.data.User;

public class UserDAO {
	
	
	 private final String URL = "jdbc:postgresql://localhost:5432/moviecompass";
	 private final String USER = "postgres";
	 private final String PASSWORD = "test";
	 
	 public UserDAO() { 
		 try {
			 Class.forName("org.postgresql.Driver");
		 } catch (ClassNotFoundException e) {
			e.printStackTrace();	
		 }
	 }
	 
	 //ユーザ登録があるかを検索(完全一致)
	 public User userSearch(String mail, String pass) {
		 User loginUser = null;
		 
		 String sql = "SELECT * ";
		 
		 sql += "FROM \"User\" ";
		 sql += "WHERE user_mail_address = ? ";
		 sql += "AND user_pass = ? ;";
		 
		 try (Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
					PreparedStatement st = con.prepareStatement(sql);) {

			 st.setString(1, mail);
			 st.setString(2, pass);
			 
			 System.out.println(st);
			 
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