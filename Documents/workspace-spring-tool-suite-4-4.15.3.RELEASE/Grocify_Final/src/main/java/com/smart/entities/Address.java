package com.smart.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ADDRESS")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int aId;
	private String firstName;
	private String lastName;
	private String address1;
	private String address2;
	private String pinCode;
	private String City;
	private String phone;

	@ManyToOne
	private User user;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getaId() {
		return aId;
	}

	public void setaId(int aId) {
		this.aId = aId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [aId=" + aId + ", firstName=" + firstName + ", lastName=" + lastName + ", address1=" + address1
				+ ", address2=" + address2 + ", pinCode=" + pinCode + ", City=" + City + ", phone=" + phone + ", user="
				+ user + "]";
	}

}
