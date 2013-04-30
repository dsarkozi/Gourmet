package com.gourmet6;

import java.sql.Timestamp;

public class Client {
	private String email;
	private String name;
	private String phone;
	
	public Client(String email, String name, String phone)
	{
		this.email = email;
		this.name = name;
		this.phone = phone;
	}
	
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
	
	public Reservation createReservation(String time, int people)
	{
		//TODO Récupèrer l'objet Restaurant.
		//Reservation greed = new Reservation(time,people,this.name,this.email);
		return new Reservation();
		
	}
	
	public Reservation createReservationWithOrder(Timestamp time, int people, Order order)
	{
		//TODO Récupèrer l'objet Restaurant.
		//Reservation greed = new Reservation(time,people,this.name,this.email,order);
		return new Reservation();
		
	}
	
	public Order createOrder()
	{
		Order odd = new Order(this.name,this.email);
		return odd;
		
	}
	
	public void rate(Restaurant res, byte value)
	{
		res.rateRestaurant(value);
	}
}
