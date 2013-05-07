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
	private String name;
	private String email;
	private GregorianCalendar date;
	private int people;
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
	public Reservation()
	{
	}
	

	/**********************
	 * Getters and setters
	 **********************/

	public Restaurant getReservationResName()
	{
		return restaurant;
	}
	public void setReservationRestaurant(Restaurant restaurant)
	{
		this.restaurant = restaurant;
	}
	public String getReservationName()
	{
		return name;
	}
	public void setReservationName(String name)
	{
		this.name = name;
	}
	public String getReservationEmail()
	{
		return email;
	}
	public void setReservationEmail(String email)
	{
		this.email = email;
	}
	public GregorianCalendar getReservationTime()
	{
		return date;
	}
	public void setReservationDate(String date)
	{
		this.date = restaurant.parseDate(date);
	}
	public void setReservationDate(GregorianCalendar date)
	{
		this.date = date;
	}
	public int getReservationPeople()
	{
		return people;
	}
	public void setReservationPeople(int people)
	{
		this.people = people;
	}
	public Order getOrder()
	{
		return order;
	}
	public void setOrder(Order order)
	{
		this.order = order;
	}
}
