package com.gourmet6;

import java.util.ArrayList;

/**
 * @author Lena
 *
 */
public class Order {
	
	private ArrayList<Dish> dishes;
	private String name;
	private String mail;
	
	public Order(String name, String mail)
	{
		this.dishes = null;
		this.setOrderName(name);
		this.setOrderMail(mail);
	}
	
	/**
	 * @return the dishes
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
	 * @return the name
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
	 * @return the mail
	 */
	public String getOrderMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setOrderMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @param dish the dish to add to the arraylist of dishes
	 */
	public void addDish(Dish dish) {
		dishes.add(dish);
	}

	/**
	 * @return the total price of the order
	 */
	public double getOrderPrice() {
		double totalPrice = 0;
		for (Dish d : dishes)
			totalPrice += (d.getPrice()*d.getInventory());
		return totalPrice;
	}

}
