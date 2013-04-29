package com.gourmet6;

import java.sql.Timestamp;

public class TimeTable {
	
	private Timestamp openTime;
	private Timestamp closingTime;
	
	public TimeTable(Timestamp openTime, Timestamp closingTime){
		this.openTime = openTime;
		this.closingTime = closingTime;
	}
	
	public Timestamp getOpenTime() {
		return this.openTime;
	}
	public void setOpenTime(Timestamp openTime) {
		this.openTime = openTime;
	}
	public Timestamp getClosingTime() {
		return this.closingTime;
	}
	public void setClosingTime(Timestamp closingTime) {
		this.closingTime = closingTime;
	}

}
