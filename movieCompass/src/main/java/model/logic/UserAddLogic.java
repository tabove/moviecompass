package model.data;

import dao.UserDAO;

public class UserAddLogic{
	
	public boolean add(
			String mail,
			String pass,
			String name) {
		
		// 追加する商品を表したuserインスタンスを準備
		User user = new User(
				mail,
				pass,
				name);
		// 追加処理
		UserDAO dao = new UserDAO();
		int count = dao.userList(user);

		// 追加結果を戻り値で返す
		if (count == 0) {
			return false;
		} else {
			return true;
		}
	}
}
