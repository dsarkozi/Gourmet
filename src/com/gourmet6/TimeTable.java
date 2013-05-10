package com.gourmet6;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.util.Log;

public class TimeTable 
{
	
	private String jourDebut;
	private String jourFin;
	private GregorianCalendar openTime;
	private GregorianCalendar closingTime;
	
	public TimeTable(String jourDebut, String jourFin, GregorianCalendar openTime, GregorianCalendar closingTime)
	{
		this.setJourDebut(jourDebut);
		this.setJourFin(jourFin);
		this.openTime = openTime;
		this.closingTime = closingTime;
	}
	
	public TimeTable(String jourDebut, String jourFin, String openTime, String closingTime)
	{
		this.setJourDebut(jourDebut);
		this.setJourFin(jourFin);
		this.openTime = parseDate(openTime);
		this.closingTime = parseDate(closingTime);
	}
	
    static public HashMap<String,Integer> weekMap = new HashMap<String,Integer>(7);
    static {
    	weekMap.put("dimanche", 1);
    	weekMap.put("lundi", 	2);
    	weekMap.put("mardi", 	3);
    	weekMap.put("mercredi", 4);
    	weekMap.put("jeudi", 	5);
    	weekMap.put("vendredi", 6);
    	weekMap.put("samedi", 	7);
    }
    
    static public HashMap<String,String> translateWeek = new HashMap<String,String>(7);
    static {
    	translateWeek.put("dimanche", "Sunday");
    	translateWeek.put("lundi", 	  "Monday");
    	translateWeek.put("mardi", 	  "Tuesday");
    	translateWeek.put("mercredi", "Wednesday");
    	translateWeek.put("jeudi", 	  "Thursday");
    	translateWeek.put("vendredi", "Friday");
    	translateWeek.put("samedi",	  "Saterday");
    }
    
    static public HashMap<Integer,String> mapWeek = new HashMap<Integer,String>(7);
    static {
    	mapWeek.put(1, "Sundays");
    	mapWeek.put(2, "Monday");
    	mapWeek.put(3, "Tuesday");
    	mapWeek.put(4, "Wednesday");
    	mapWeek.put(5, "Thursday");
    	mapWeek.put(6, "Friday");
    	mapWeek.put(7, "Saterday");
    }
    
	
	/***********************
	 * Formatting functions
	 ***********************/
	/**
	 * Formats a String containing a date and/or time into a GregorianCalendar object representing the date.
	 * @param date the date to parse, may be one of the following 3 formats :
	 * 			dd/MM/yyyy hh:mm
	 * 			dd-MM-yyyy hh:mm
	 * 			hh:mm  
	 * @return a GregorianCalendar representing the date originally contained in the String.
	 * 		   If the format is not respected, returns null.
	 */
	@SuppressLint("SimpleDateFormat")
	public static GregorianCalendar parseDate(String date){
		SimpleDateFormat ourFormat;
		TimeZone timezone = TimeZone.getDefault();
		GregorianCalendar cal = null;
		if (date.contains("/") && date.contains(":"))
		{
			ourFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
		}
		else if (date.contains("-") && date.contains(":"))
		{
			ourFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		}
		else if (date.contains(":") && (!date.contains("/") || !date.contains("-")))
		{
			ourFormat = new SimpleDateFormat("hh:mm");
		}
		else
		{
			Log.e("Timetable", "Date's format unknow");
			return cal;
		}
		try
		{
			cal = new GregorianCalendar();
			cal.setTime(ourFormat.parse(date));
			cal.setTimeZone(timezone);
		}
		catch (ParseException e)
		{
			Log.e("Timetable", "Error encoding the date : "+e);
			e.printStackTrace();
		}
		return cal;
	}
	
	public static String parseDateInString(GregorianCalendar temp)
	{
		String year = temp.get(GregorianCalendar.YEAR)+"-"; String month;
		if((temp.get(GregorianCalendar.MONTH)+1)<10){
			month = "0"+(temp.get(GregorianCalendar.MONTH)+1)+"-";
		}
		else {
			month = (temp.get(GregorianCalendar.MONTH)+1)+"-";
		} String day;
		if(temp.get(GregorianCalendar.DAY_OF_MONTH)<10){
			day = "0"+temp.get(GregorianCalendar.DAY_OF_MONTH)+" ";
		}
		else{
			day = temp.get(GregorianCalendar.DAY_OF_MONTH)+" ";
		}String hour;
		if(temp.get(GregorianCalendar.HOUR_OF_DAY)<10){
			hour = "0"+temp.get(GregorianCalendar.HOUR_OF_DAY)+":";
		}
		else{
			hour = temp.get(GregorianCalendar.HOUR_OF_DAY)+":";
		} String minute;
		if(temp.get(GregorianCalendar.MINUTE)<10){
			minute = "0"+temp.get(GregorianCalendar.MINUTE);
		}
		else{
			minute = temp.get(GregorianCalendar.MINUTE)+"";
		}

		return year+month+day+hour+minute;
	}
	
	public String parseInString()
	{
		if(this.getJourDebut().equals(this.getJourFin()))
		{
			return translateWeek.get(this.getJourDebut())+" :  "+parseHorairInString(this.getOpenTime())+" - "
					+parseHorairInString(this.getClosingTime());
		}
		else
		{
			return translateWeek.get(this.getJourDebut())+" - "+translateWeek.get(this.getJourFin())
					+" :  "+parseHorairInString(this.getOpenTime())+" - "
					+parseHorairInString(this.getClosingTime());
		}
	}
	
	public String parseHorairInString(GregorianCalendar cal)
	{
		String hour;
		if(cal.get(GregorianCalendar.HOUR_OF_DAY)<10){
			hour = "0"+cal.get(GregorianCalendar.HOUR_OF_DAY)+":";
		}
		else{
			hour = cal.get(GregorianCalendar.HOUR_OF_DAY)+":";
		} String minute;
		if(cal.get(GregorianCalendar.MINUTE)<10){
			minute = "0"+cal.get(GregorianCalendar.MINUTE);
		}
		else{
			minute = cal.get(GregorianCalendar.MINUTE)+"";
		}
		return hour+minute;
	}
	
	public static String parseDateInStringForReservation(GregorianCalendar temp) {
		String jour = mapWeek.get(temp.get(GregorianCalendar.DAY_OF_WEEK));
		String year = temp.get(GregorianCalendar.YEAR)+" "; String month;
		if((temp.get(GregorianCalendar.MONTH)+1)<10){
			month = "0"+(temp.get(GregorianCalendar.MONTH)+1)+"-";
		}
		else {
			month = (temp.get(GregorianCalendar.MONTH)+1)+"-";
		} String day;
		if(temp.get(GregorianCalendar.DAY_OF_MONTH)<10){
			day = "0"+temp.get(GregorianCalendar.DAY_OF_MONTH)+"-";
		}
		else{
			day = temp.get(GregorianCalendar.DAY_OF_MONTH)+"-";
		}String hour;
		if(temp.get(GregorianCalendar.HOUR_OF_DAY)<10){
			hour = "0"+temp.get(GregorianCalendar.HOUR_OF_DAY)+":";
		}
		else{
			hour = temp.get(GregorianCalendar.HOUR_OF_DAY)+":";
		} String minute;
		if(temp.get(GregorianCalendar.MINUTE)<10){
			minute = "0"+temp.get(GregorianCalendar.MINUTE);
		}
		else{
			minute = temp.get(GregorianCalendar.MINUTE)+"";
		}

		return jour+" "+day+month+year+hour+minute;
	}
	
	public boolean isInTimeTable(GregorianCalendar date)
	{
		GregorianCalendar dateDebut = new GregorianCalendar(date.get(GregorianCalendar.YEAR), 
				date.get(GregorianCalendar.MONTH), date.get(GregorianCalendar.DAY_OF_MONTH), 
				date.get(GregorianCalendar.HOUR_OF_DAY), date.get(GregorianCalendar.MINUTE));
		while(weekMap.get(this.getJourDebut()) != dateDebut.get(GregorianCalendar.DAY_OF_WEEK))
		{
			dateDebut.set(GregorianCalendar.DAY_OF_WEEK, dateDebut.get(GregorianCalendar.DAY_OF_WEEK)-1);
		}
		dateDebut.set(GregorianCalendar.HOUR_OF_DAY, this.getOpenTime().get(GregorianCalendar.HOUR_OF_DAY));
		dateDebut.set(GregorianCalendar.MINUTE, this.getOpenTime().get(GregorianCalendar.MINUTE));
		
		GregorianCalendar dateFin = new GregorianCalendar(date.get(GregorianCalendar.YEAR), 
				date.get(GregorianCalendar.MONTH), date.get(GregorianCalendar.DAY_OF_MONTH), 
				date.get(GregorianCalendar.HOUR_OF_DAY), date.get(GregorianCalendar.MINUTE));
		while(weekMap.get(this.jourFin) != dateFin.get(GregorianCalendar.DAY_OF_WEEK))
		{
			dateFin.set(GregorianCalendar.DAY_OF_WEEK, dateFin.get(GregorianCalendar.DAY_OF_WEEK)-1);
		}
		dateFin.set(GregorianCalendar.HOUR_OF_DAY, this.getClosingTime().get(GregorianCalendar.HOUR_OF_DAY));
		dateFin.set(GregorianCalendar.MINUTE, this.getClosingTime().get(GregorianCalendar.MINUTE));
		
		return (date.compareTo(dateDebut)>0 || date.compareTo(dateDebut)==0) 
				&& checkLimit(dateDebut, dateFin, date)
				&& checkTime(date);
	}
	private boolean checkTime(GregorianCalendar date) {
		int heureFin = this.getClosingTime().get(GregorianCalendar.HOUR_OF_DAY);
		int heureDebut = this.getOpenTime().get(GregorianCalendar.HOUR_OF_DAY);
		int heureDate = date.get(GregorianCalendar.HOUR_OF_DAY);
		if(heureFin<heureDebut)
		{
			if(0<=heureDate && heureDate<=heureFin){
				heureDebut=0;
			}
			else{
				heureFin +=24;
			}
			
		}
		if(heureDebut<heureDate && heureDate<heureFin)
		{
			return true;
		}
		else if(heureDebut==heureDate && heureDate<heureFin)
		{
			if(this.getOpenTime().get(GregorianCalendar.MINUTE)<=date.get(GregorianCalendar.MINUTE)){
				return true;
			}
			else{
				return false;
			}
		}
		else if(heureDebut<heureDate && heureDate==heureFin)
		{
			if(date.get(GregorianCalendar.MINUTE)<=this.getClosingTime().get(GregorianCalendar.MINUTE)){
				return true;
			}
			else{
				return false;
			}
		}
		else if(heureDebut==heureDate && heureDate==heureFin)
		{
			if(this.getOpenTime().get(GregorianCalendar.MINUTE)<=date.get(GregorianCalendar.MINUTE)
					&& date.get(GregorianCalendar.MINUTE)<=this.getClosingTime().get(GregorianCalendar.MINUTE))
			{
				return true;
			}
			else{
				return false;
			}
		}
		else{
			return false;
		}
	}
	
	public boolean checkLimit(GregorianCalendar dateDebut, GregorianCalendar dateFin, GregorianCalendar date)
	{
		if(date.compareTo(dateFin)<0)
		{
			return true;
		}
		else
		{
			int heureFin = this.getClosingTime().get(GregorianCalendar.HOUR_OF_DAY);
			int heureDebut = this.getOpenTime().get(GregorianCalendar.HOUR_OF_DAY);
			int heureDate = date.get(GregorianCalendar.HOUR_OF_DAY);
			if(date.get(GregorianCalendar.YEAR) == dateFin.get(GregorianCalendar.YEAR)
					&& date.get(GregorianCalendar.MONTH) == dateFin.get(GregorianCalendar.MONDAY)
					&& date.get(GregorianCalendar.DAY_OF_MONTH) == dateFin.get(GregorianCalendar.DAY_OF_MONTH)
					&& heureFin<heureDebut)
			{
				if(heureDate-24<=heureFin){
					return true;
				}
				else return false;
			}
			else return false;
		}
	}
	
	/**********************
	 * Getters and setters
	 **********************/
	public GregorianCalendar getOpenTime()
	{
		return this.openTime;
	}
	public void setOpenTime(GregorianCalendar openTime)
	{
		this.openTime = openTime;
	}
	public void setOpenTime(String openTime)
	{
		this.openTime = parseDate(openTime);
	}
	public GregorianCalendar getClosingTime()
	{
		return this.closingTime;
	}
	public void setClosingTime(GregorianCalendar closingTime)
	{
		this.closingTime = closingTime;
	}
	public void setClosingTime(String closingTime)
	{
		this.closingTime = parseDate(closingTime);
	}
	public String getJourDebut()
	{
		return jourDebut;
	}
	public void setJourDebut(String jourDebut) 
	{
		this.jourDebut = jourDebut;
	}

	public String getJourFin()
	{
		return jourFin;
	}
	public void setJourFin(String jourFin)
	{
		this.jourFin = jourFin;
	}
}
