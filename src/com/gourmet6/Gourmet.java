package com.gourmet6;

import android.app.Application;

public class Gourmet extends Application{
	
	private Client currentcli = null;
	private Restaurant currentres = null;
	
	public Client getClient() 
	{
		return currentcli;
	}
	
	public void setClient(Client currentcli) 
	{
		this.currentcli = currentcli;
	}
	
	public Restaurant getRest() 
	{
		return currentres;
	}
	
	public void setRest(Restaurant currentres) 
	{
		this.currentres = currentres;
	}
	
}
