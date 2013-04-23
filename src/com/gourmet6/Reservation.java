package com.gourmet6;

import java.sql.Timestamp;

/**
 * @author Lena
 *
 */
public class Reservation {
	
	private Timestamp time;
	private int people;
	private String name;
	private String mail;
	private Order order;
	
	public Reservation(Timestamp time, int people, String name, String mail)
	{
		this.time = time;
		this.people = people;
		this.name = name;
		this.mail = mail;	
		this.setOrder(null);
	}
	
	public Reservation(Timestamp time, int people, String name, String mail, Order order)
	{
		this.time = time;
		this.people = people;
		this.name = name;
		this.mail = mail;
		this.setOrder(order);
	}
	
	public Timestamp getReservationTime() {
		return time;
	}
	public void setReservationTime(Timestamp time) {
		this.time = time;
	}
	public int getReservationPeople() {
		return people;
	}
	public void setReservationPeople(int people) {
		this.people = people;
	}
	
	/**
	 * @return the name
	 */
	public String getReservationName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setReservationName(String name) {
		this.name = name;
	}
	/**
	 * @return the mail
	 */
	public String getReservationMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setReservationMail(String mail) {
		this.mail = mail;
	}
	
	/**
	 * @return the order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	void addOrder(Order order) {
		this.setOrder(order);	
	}

}
