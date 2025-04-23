package model.logic;

import dao.UserDAO;
import model.data.User;


public class UserSearchLogic {

	/*
	 * String mailと一致するレコードをUserテーブルより検索。
	 * レコードを発見した場合、String passとレコードのパスワード
	 * が完全一致するか確認。一致した場合のみ、Userインスタンスを返す。
	 */
	public User search(String mail, String pass) {
		if (mail == null || pass == null) {
			return null;
		}
		
		UserDAO dao = new UserDAO();
		User u = dao.userSearch(mail);
		
		// 引数のmailで登録されているユーザが見つけられなかった場合
		if (u == null) {
			return null;
		}
		
		// 引数のpassが登録されているパスワードと完全一致
		if (pass.equals(u.getPass())) {
			return u;
			
		// 不一致の場合
		} else {
			return null;
		}
	}
	
	/*
	 * String mailと一致するレコードをUserテーブルより検索。
	 * レコードを発見した場合にTrueを返して、発見出来なかった
	 * 場合にFalseを返す。
	 */
	public boolean searchByMail(String mail) {
		UserDAO dao = new UserDAO();
		User u = dao.userSearch(mail);
	
		if (u != null) {
			return true;
		} else {
			return false;
		}
		
	}
}
