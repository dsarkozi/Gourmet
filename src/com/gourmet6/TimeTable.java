package com.gourmet6;

import java.util.GregorianCalendar;

public class TimeTable {
	
	private GregorianCalendar openTime;
	private GregorianCalendar closingTime;
	
	public TimeTable(GregorianCalendar openTime, GregorianCalendar closingTime){
		this.openTime = openTime;
		this.closingTime = closingTime;
	}
	
	public TimeTable(String opentTime, String closingTime){
		//TODO
	}
	
	public GregorianCalendar getOpenTime() {
		return this.openTime;
	}
	public void setOpenTime(GregorianCalendar openTime) {
		this.openTime = openTime;
	}
	public void setOpenTime(String openTime){
		//TODO
	}
	public GregorianCalendar getClosingTime() {
		return this.closingTime;
	}
	public void setClosingTime(GregorianCalendar closingTime) {
		this.closingTime = closingTime;
	}
	public void setClosingTime(String closingTime){
		//TODO
	}

}
