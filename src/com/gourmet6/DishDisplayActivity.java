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
	
	private TextView price;
	private TextView type;
	private TextView subtype;
	private TextView inventory;
	private TextView allergens;
	
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
		String imgType = Restaurant.getNameImg(plat.getType());
		dishImage.setImageResource(getResources().getIdentifier(imgType, "drawable", getPackageName()));
		dishDescr  = (TextView)findViewById(R.id.descr);
		dishDescr.setText(plat.getDescription());
		
		price = (TextView)findViewById(R.id.price);
		type = (TextView)findViewById(R.id.type);
		subtype = (TextView)findViewById(R.id.subtype);
		inventory = (TextView)findViewById(R.id.inventory);
		allergens = (TextView)findViewById(R.id.allergens);
		
		price.setText(Dish.myRound(plat.getPrice())+"");
		type.setText(plat.getType());
		subtype.setText(plat.getSubtype());
		inventory.setText(plat.getInventory());
		allergens.setText(arrayListOfStringToString(plat.getAllergens()));
		
		dishInfo  = (TextView)findViewById(R.id.info);
		dishInfo.setText("Info about this dish :\n"+price+type+subtype+inventory+allergens);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dish_display, menu);
		return true;
	}
	
	public static String arrayListOfStringToString(ArrayList<String> str){
		if(str==null) return "None";
		String s = str.get(0);
		for(int i=1; i<str.size(); i++){
			s = s +", "+str.get(i);
		}
		return s;
	}
}
