package com.gourmet6;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DishDisplayActivity extends Activity {

	private Gourmet g;
	private Dish plat;
	private Restaurant current;
	
	private TextView dishName;
	private ImageView dishImage;
	private TextView dishDescr;
	private TextView dishInfo;
	
	private String name;
	private String price;
	private String type;
	private String subtype;
	private String inventory;
	private String allergens;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_display);
		
		g = (Gourmet)getApplication();
		current = g.getRest();
		
		Bundle extra = getIntent().getExtras();
		plat = current.getDish(extra.getString("the_dish"));
		
		name = plat.getName();
		dishName  = (TextView)findViewById(R.id.dishname);
		dishName.setText(name);
		
		dishImage = (ImageView)findViewById(R.id.dishimage);
		//dishImage.setImageResource(1234); // a faire mettre la ressource de l'image :/
		String imgName = Restaurant.getNameImg(name);
		/*
		if(getIdentifier(imgName, "drawable", getPackageName()).exists()){
			
		}
		elseif(){
			
		}*/
		dishImage.setImageResource(getResources().getIdentifier(imgName, "drawable", getPackageName()));
		
		dishDescr  = (TextView)findViewById(R.id.dishdescr);
		dishDescr.setText(plat.getDescription()); //"Description : "+
		
		price ="-price : "+plat.getPrice()+"\n";
		type ="-type : "+plat.getType()+"\n";
		subtype="-subtype : "+plat.getSubtype()+"\n";
		inventory="-inventory : "+plat.getInventory()+"\n";
		allergens="-allergens : "+arrayListOfStringToString(plat.getAllergens());
		
		dishInfo  = (TextView)findViewById(R.id.dishinfo);
		dishInfo.setText("Info about this dish :\n"+price+type+subtype+inventory+allergens);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dish_display, menu);
		return true;
	}
	
	public static String arrayListOfStringToString(ArrayList<String> str){
		String s = str.get(0);
		for(int i=1; i<str.size(); i++){
			s = s +", "+str.get(i);
		}
		return s;
	}
}
