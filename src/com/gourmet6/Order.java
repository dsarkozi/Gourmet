package com.gourmet6;

import java.util.ArrayList;

/**
 * A class representing an order and its properties.
 * 
 * @author Group 6
 * @version 06.05.2013
 */
public class Order {
	
	private ArrayList<Dish> dishes;
	private String name;
	private String email;
	
	/**
	 * @param name the name with which to address the client
	 * @param email the email related to the account from which the booking was made
	 */
	public Order(String name, String email)
	{
		this.dishes = null;
		this.name = name;
		this.email = email;
	}
	
	/**
	 * @return the dishes of the order
	 */
	public ArrayList<Dish> getOrderDishes() {
		return dishes;
	}
	/**
	 * @param dishes the dishes to set
	 */
	public void setOrderDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}

	/**
	 * @return the name of the client who made the order
	 */
	public String getOrderName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setOrderName(String name) {
		this.name = name;
	}

	/**
	 * @return the email of the client who made the order
	 */
	public String getOrderEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setOrderEmail(String email) {
		this.email = email;
	}

	/**
	 * Adds a given quantity of a dish to the order.
	 * @param dish the dish to add to the arraylist of dishes
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
			totalPrice += (d.getPrice()*d.getInventory());
		return totalPrice;
	}
}
