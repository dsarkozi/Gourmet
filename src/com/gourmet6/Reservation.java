package com.gourmet6;

import java.sql.Timestamp;

/**
 * @author Lena
 *
 */
public class Reservation {
	
	private int number;
	private Timestamp time;
	private int people;
	
	public int getReservationNumber() {
		return number;
	}
	public void setReservationNumber(int number) {
		this.number = number;
	}
	public Timestamp getReservationTime() {
		return time;
	}
	public void setReservationTime(Timestamp time) {
		this.time = time;
	}
	public int getReservationPeople() {
		return people;
	}
	public void setReservationPeople(int people) {
		this.people = people;
	}
	
	void addOrder(Order order) {
		
	}

}
