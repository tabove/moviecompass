package model.data;

import java.io.Serializable;

public class User implements Serializable{
	private String id;
	private String mail;
	private String pass;
	private String name;
	private String torokubi;
	
	public User() {
	}
	public User(String mail, String pass) {
		this.mail = mail;
		this.pass = pass;
	}
	public User(String mail, String pass, String name) {
		this.mail = mail;
		this.pass = pass;
		this.name = name;
	}
	public User(String id, String mail, String pass, String name, String torokubi) {
		this.id = id;
		this.mail = mail;
		this.pass = pass;
		this.name = name;
		this.torokubi = torokubi;
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTorokubi() {
		return torokubi;
	}

	public void setTorokubi(String torokubi) {
		this.torokubi = torokubi;
	}
	
}
