package model.data;

import java.io.Serializable;

public class Cinema implements Serializable {
	private String id;
	private String name;
	private String address;
	private String phone_number;
	
	public Cinema() {}
	public Cinema(String cinema_id, String cinema_name, String address, String phone_number) {
		this.id = cinema_id;
		this.name = cinema_name;
		this.address = address;
		this.phone_number = phone_number;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone_number() {
		return phone_number;
	}
	
}
