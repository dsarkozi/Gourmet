package com.gourmet6;

import java.util.ArrayList;

/**
 * @author Lena
 *
 */
public class Order {
	
	private int number;
	private ArrayList<Dish> dishes;
	
	/**
	 * @return the number
	 */
	public int getOrderNumber() {
		return number;
	}
	/**
	 * @param number the number to set
	 */
	public void setOrderNumber(int number) {
		this.number = number;
	}
	public ArrayList<Dish> getOrderDishes() {
		return dishes;
	}
	public void setOrderDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
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
