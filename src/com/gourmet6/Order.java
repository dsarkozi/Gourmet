package com.gourmet6;

import java.util.ArrayList;

/**
 * A class representing an order and its properties.
 * 
 * @author Group 6
 * @version 06.05.2013
 */
public class Order {
	
	private String restaurant;
	private String name;
	private String email;
	private ArrayList<Dish> dishes;

	public Order(String restaurant, String name, String email)
	{
		this.restaurant = restaurant;
		this.name = name;
		this.email = email;
		this.dishes = null;
	}
	
	/**
	 * Adds a given quantity of a dish to the order.
	 * @param dish the dish to add to the ArrayList of dishes
	 * @param quantity the amount of this dish that has to be added
	 */
	public void addDish(Dish dish, int quantity) {
		dish.setQuantity(quantity);
		dishes.add(dish);
	}

	/**
	 * Computes the total price of an order.
	 * @return the total price of the order
	 */
	public double getOrderPrice() {
		double totalPrice = 0;
		for (Dish d : dishes)
		{
			totalPrice += (d.getPrice()*d.getInventory());
		}
		return totalPrice;
	}
	
	/**********************
	 * Getters and setters
	 **********************/
	public String getOrderRestaurant()
	{
		return restaurant;
	}
	public void setOrderRestaurant(String restaurant)
	{
		this.restaurant = restaurant;
	}
	public String getOrderName()
	{
		return name;
	}
	public void setOrderName(String name)
	{
		this.name = name;
	}
	public String getOrderEmail()
	{
		return email;
	}
	public void setOrderEmail(String email)
	{
		this.email = email;
	}	public ArrayList<Dish> getOrderDishes()
	{
		return dishes;
	}
	public void setOrderDishes(ArrayList<Dish> dishes)
	{
		this.dishes = dishes;
	}
}
