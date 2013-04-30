package com.gourmet6;

import java.util.GregorianCalendar;

/**
 * @author Lena
 *
 */
public class Reservation {
	
	private Restaurant restaurant;
	private GregorianCalendar date;
	private int people;
	private String name;
	private String mail;
	private Order order;
	
	public Reservation(Restaurant restaurant, String date, int people, String name, String mail)
	{
		this.restaurant = restaurant;
		this.date = restaurant.parseDate(date);
		this.people = people;
		this.name = name;
		this.mail = mail;
	}
	public Reservation(Restaurant restaurant, String date, int people, String name, String mail, Order order)
	{
		this.restaurant = restaurant;
		this.date = restaurant.parseDate(date);
		this.people = people;
		this.name = name;
		this.mail = mail;
		this.order = order;
	}
	
	public Reservation() {
	}
	
	/**
	 * @return the time
	 */
	public GregorianCalendar getReservationTime() {
		return date;
	}
	/**
	 * @param time the time to set
	 */
	public void setReservationTime(String date) {
		this.date = restaurant.parseDate(date);
	}
	public void setReservationTime(GregorianCalendar date){
		this.date = date;
	}
	
	/**
	 * @return the people
	 */
	public int getReservationPeople() {
		return people;
	}
	/**
	 * @param people the people to set
	 */
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

	/**
	 * @param order the order to add to the reservation
	 * BE CAREFULL NOT TO ADD AN ORDER IF THERE ALREADY IS ONE!!
	 */
	void addOrder(Order order) {
		this.setOrder(order);	
	}

}
