package model.logic;

import dao.UserDAO;
import model.data.User;


public class UserSearchLogic {

	public User search(String mail) {
		UserDAO dao = new UserDAO();
		User u = dao.userSearch(mail);
	
		return u;

	}
}
