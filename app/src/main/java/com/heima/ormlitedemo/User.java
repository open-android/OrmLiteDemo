package com.heima.ormlitedemo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "t_user")
public class User {
	@DatabaseField(id = true)
	private int id;
	@DatabaseField(columnName = "name")
	private String name;
	@DatabaseField(columnName = "balance")
	private float balance;
	@DatabaseField(columnName = "discount")
	private int discount;
	@DatabaseField(columnName = "integral")
	private int integral;
	@DatabaseField(columnName = "phone")
	private String phone;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User() {
	}

	public User(int id, String name, float balance, int discount, int integral, String phone) {
		this.id = id;
		this.name = name;
		this.balance = balance;
		this.discount = discount;
		this.integral = integral;
		this.phone = phone;
	}
}
