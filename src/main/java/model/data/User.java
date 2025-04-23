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
	public String getMail() {
		return mail;
	}
	public String getPass() {
		return pass;
	}
	public String getName() {
		return name;
	}
	public String getTorokubi() {
		return torokubi;
	}
	
}
