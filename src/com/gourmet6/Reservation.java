package com.gourmet6;

import java.util.GregorianCalendar;

/**
 * A class representing a reservation and its properties.
 * A reservation may hold an order object.
 * 
 * @author Group 6
 * @version 06.05.2013
 */
public class Reservation {
	
	private Restaurant restaurant;
	private GregorianCalendar date;
	private int people;
	private String name;
	private String email;
	private Order order;
	
	/**
	 * Constructor
	 * @param restaurant the restaurant to make the booking in
	 * @param date the date on which to book
	 * @param people the number of people who need to be seated
	 * @param name the name with which to address the client
	 * @param email the email related to the account from which the booking was made
	 */
	public Reservation(Restaurant restaurant, String date, int people, String name, String email)
	{
		this.restaurant = restaurant;
		this.date = restaurant.parseDate(date);
		this.people = people;
		this.name = name;
		this.email = email;
	}
	
	/**
	 * Constructor
	 * @param restaurant the restaurant to make the booking in
	 * @param date the date on which to book
	 * @param people the number of people who need to be seated
	 * @param name the name with which to address the client
	 * @param email the email related to the account from which the booking was made
	 * @param order the order bound to this booking
	 */
	public Reservation(Restaurant restaurant, String date, int people, String name, String email, Order order)
	{
		this.restaurant = restaurant;
		this.date = restaurant.parseDate(date);
		this.people = people;
		this.name = name;
		this.email = email;
		this.order = order;
	}
	
	/**
	 * 
	 */
	public Reservation() {
	}
	
	/**
	 * @return the time of the reservation
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
	/**
	 * @param date the date to set
	 */
	public void setReservationTime(GregorianCalendar date){
		this.date = date;
	}
	
	/**
	 * @return the people of the reservation
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
	 * @return the name of the client who made the reservation
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
	 * @return the email of the client who made the reservation
	 */
	public String getReservationEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setReservationEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return the order bound to the reservation
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
	 */
	void addOrder(Order order) {
		this.setOrder(order);	
	}

}
