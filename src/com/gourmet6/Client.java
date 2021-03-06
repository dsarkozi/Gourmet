package com.gourmet6;

import java.util.GregorianCalendar;

/**
 * A class representing a client and his information.
 * 
 * @author Group 6
 * @version 06.05.2013
 */
public class Client
{
	private String email;
	private String name;
	private String phone;

	/**
	 * Constructor
	 * 
	 * @param email
	 *            the email which identifies the client
	 * @param name
	 *            the name whith which to address the client
	 * @param phone
	 *            the client's phone number
	 */
	public Client(String email, String name, String phone)
	{
		this.email = email;
		this.name = name;
		this.phone = phone;
	}

	/**
	 * Creates a reservation for the client.
	 * 
	 * @param time
	 *            the time and date of the booking
	 * @param people
	 *            the number of people who will need a seat
	 * @return the reservation containing the required information
	 */
	public Reservation createReservation(String restName, String date,
			int people)
	{
		return new Reservation(restName, date, people, this.name, this.email);

	}

	/**
	 * Creates a reservation for the client.
	 * 
	 * @param time
	 *            the time and date of the booking
	 * @param people
	 *            the number of people who will need a seat
	 * @return the reservation containing the required information
	 */
	public Reservation createReservation(String restName,
			GregorianCalendar date, int people)
	{
		return new Reservation(restName, date, people, this.name, this.email);

	}

	/**
	 * Creates an order.
	 * 
	 * @return an empty order
	 */
	public Order createOrder(String restName)
	{
		return new Order(restName, this.name, this.email);
	}

	/**********************
	 * Getters and setters
	 **********************/
	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}
}
