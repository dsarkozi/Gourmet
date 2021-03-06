package com.gourmet6;

import java.util.GregorianCalendar;

/**
 * A class representing a reservation and its properties. A reservation may hold
 * an order object.
 * 
 * @author Group 6
 * @version 06.05.2013
 */
public class Reservation
{

	private String restaurant;
	private String name;
	private String email;
	private GregorianCalendar date;
	private int people;
	private Order order;

	/**
	 * Constructor
	 * 
	 * @param restaurant
	 *            the restaurant to make the booking in
	 * @param date
	 *            the date on which to book
	 * @param people
	 *            the number of people who need to be seated
	 * @param name
	 *            the name with which to address the client
	 * @param email
	 *            the email related to the account from which the booking was
	 *            made
	 */
	public Reservation(String restaurant, String date, int people,
			String nameClient, String email)
	{
		this.restaurant = restaurant;
		this.date = TimeTable.parseDate(date);
		this.people = people;
		this.name = nameClient;
		this.email = email;
		this.order = null;
	}

	/**
	 * Constructor
	 * 
	 * @param restaurant
	 * @param date
	 * @param people
	 * @param nameClient
	 * @param email
	 */
	public Reservation(String restaurant, GregorianCalendar date, int people,
			String nameClient, String email)
	{
		this.restaurant = restaurant;
		this.date = date;
		this.people = people;
		this.name = nameClient;
		this.email = email;
		this.order = null;
	}

	/**
	 * Constructor
	 * 
	 * @param restaurant
	 *            the restaurant to make the booking in
	 * @param date
	 *            the date on which to book
	 * @param people
	 *            the number of people who need to be seated
	 * @param name
	 *            the name with which to address the client
	 * @param email
	 *            the email related to the account from which the booking was
	 *            made
	 * @param order
	 *            the order bound to this booking
	 */
	public Reservation(String restaurant, String date, int people, String name,
			String email, Order order)
	{
		this.restaurant = restaurant;
		this.date = TimeTable.parseDate(date);
		this.people = people;
		this.name = name;
		this.email = email;
		this.order = order;
	}

	/**********************
	 * Getters and setters
	 **********************/
	public String getReservationResName()
	{
		return restaurant;
	}

	public void setReservationRestaurant(String restaurant)
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
		this.date = TimeTable.parseDate(date);
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

	public Order getReservationOrder()
	{
		return order;
	}

	public void setReservationOrder(Order order)
	{
		this.order = order;
	}
}
