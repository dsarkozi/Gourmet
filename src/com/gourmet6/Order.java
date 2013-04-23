/**
 * 
 */
package com.gourmet6;

import java.util.ArrayList;

/**
 * @author Lena
 *
 */
public class Order {
	
	private int number;
	private ArrayList<Dish> dishes;
	
	public int getOrderNumber() {
		return number;
	}
	public void setOrderNumber(int number) {
		this.number = number;
	}
	public ArrayList<Dish> getOrderDishes() {
		return dishes;
	}
	public void setOrderDishes(ArrayList<Dish> dishes) {
		this.dishes = dishes;
	}
	public void addDish(Dish dish) {
		
	}

	public double getOrderPrice() {
		return 0.0;
		
	}

}
