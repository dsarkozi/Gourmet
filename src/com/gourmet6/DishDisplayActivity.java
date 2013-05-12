package com.gourmet6;

import java.util.ArrayList;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class DishDisplayActivity extends Activity
{

	private Gourmet g;
	private Dish plat;
	private Restaurant current;

	private ImageView dishImage;
	private TextView dishDescr;
	// private TextView dishInfo;

	private String name;

	private TextView price;
	private TextView type;
	private TextView subtype;
	private TextView inventory;
	private TextView allergens;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_display);
		setupActionBar();

		g = (Gourmet) getApplication();
		current = g.getRest();

		Bundle extra = getIntent().getExtras();
		plat = current.getDish(extra.getString("the_dish"));

		name = plat.getName();
		setTitle(name);

		dishImage = (ImageView) findViewById(R.id.dishimage);
		String imgType = Restaurant.getNameImg2(plat.getType());
		dishImage.setImageResource(getResources().getIdentifier(imgType,
				"drawable", getPackageName()));
		dishDescr = (TextView) findViewById(R.id.descr);
		dishDescr.setText(plat.getDescription());

		price = (TextView) findViewById(R.id.price);
		type = (TextView) findViewById(R.id.type);
		subtype = (TextView) findViewById(R.id.subtype);
		inventory = (TextView) findViewById(R.id.inventory);
		allergens = (TextView) findViewById(R.id.allergens);

		price.setText(Dish.myRound(plat.getPrice()) + " \u20ac");
		type.setText(plat.getType());
		subtype.setText(plat.getSubtype());
		inventory.setText(plat.getInventory() + " pcs");
		allergens.setText(arrayListOfStringToString(plat.getAllergens()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		if (g.getClient() != null)
			getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				Intent clientGo = new Intent(DishDisplayActivity.this,
						ClientActivity.class);
				startActivity(clientGo);
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar()
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	public static String arrayListOfStringToString(ArrayList<String> str)
	{
		if (str == null) return "None";
		String s = str.get(0);
		for (int i = 1; i < str.size(); i++)
		{
			s = s + ", " + str.get(i);
		}
		return s;
	}
}
