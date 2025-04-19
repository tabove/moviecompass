package model.logic;

import dao.UserDAO;
import model.data.User;


public class UserSearchLogic {

	public User search(String mail, String pass) {
		UserDAO dao = new UserDAO();
		User u = dao.userSearch(mail, pass);
	
		return u;
		
	}
}
