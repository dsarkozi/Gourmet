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
		this.setOrderName(name);
		this.setOrderMail(mail);
		this.dishes = null;
	}
	
	public ArrayList<Dish> getOrderDishes() {
		return dishes;
	}
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
	 * @param dish not empty
	 */
	public void addDish(Dish dish) {
		dishes.add(dish);
	}

	/**
	 * @return the total price
	 */
	public double getOrderPrice() {
		double totalPrice = 0;
		for (Dish d : dishes)
			totalPrice += d.getPrice();
		return totalPrice;
	}

}
