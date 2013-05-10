package com.gourmet6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

@SuppressWarnings("deprecation")
public class RestaurantListActivity extends ListActivity
{
	private static final String TITLE = "Restaurant list";
	private DBHandler dbHand;
	private ArrayList<String> restaurants;
	private ArrayList<String> resNames;
	private Gourmet g;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_town);
		dbHand = new DBHandler(this);
		// Show the Up button in the action bar.
		setupActionBar();
		resNames = new ArrayList<String>();
		setTitle(TITLE);
		g = (Gourmet) getApplication();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Intent intent = getIntent();
		String currentTown = intent.getStringExtra("currentTown");
		ListAdapter adapter;
		if (g.getLocation() != null)
		{
			ArrayList<Location> locations = new ArrayList<Location>();
			ArrayList<HashMap<String, String>> restaurants = new ArrayList<HashMap<String, String>>();
			try
			{
				this.restaurants = dbHand.getAllResNames(currentTown); 
				locations = dbHand.getAllResNamesLocation(currentTown);
			}
			catch (SQLiteException e)
			{
				ExceptionHandler.caughtException(this, e);
			}
			final Location current = g.getLocation();
			Collections.sort(locations, new Comparator<Location>()
			{
				@Override
				public int compare(Location lhs, Location rhs)
				{
					if (current.distanceTo(lhs) - current.distanceTo(rhs) > 0)
						return 1;
					else if (current.distanceTo(lhs) - current.distanceTo(rhs) < 0)
						return -1;
					else return 0;
				}
			});
			for (Location loc : locations)
			{
				HashMap<String, String> data = new HashMap<String, String>();
				data.put("restaurant",
						loc.getExtras().getString("restaurant").split("_")[0]);
				data.put("distance",
						String.format("%.2f", current.distanceTo(loc) / 1000)
								+ " km");
				restaurants.add(data);
			}
			adapter = new SimpleAdapter(this, restaurants,
					android.R.layout.simple_list_item_2, new String[]
					{ "restaurant", "distance" }, new int[]
					{ android.R.id.text1, android.R.id.text2 });
		}
		else
		{
			try
			{
				restaurants = dbHand.getAllResNames(currentTown);
			}
			catch (SQLiteException e)
			{
				ExceptionHandler.caughtException(this, e);
			}
			for (String resto : restaurants)
			{
				String[] restos = resto.split("_");
				resNames.add(restos[0]);
			}
			adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, resNames);
		}
		setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		TextView selection;
		if (v instanceof TextView)
			selection = (TextView) v;
		else
		{
			TwoLineListItem list = (TwoLineListItem) v;
			selection = list.getText1();
		}
		Intent returnIntent = new Intent();
		String resName = selection.getText().toString();
		for (String name : restaurants)
		{
			if (name.contains(resName))
			{
				resName = name;
				break;
			}
		}
		returnIntent.putExtra("selection", resName);
		setResult(RESULT_OK, returnIntent);
		finish();
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

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
	 * menu; this adds items to the action bar if it is present.
	 * if(g.getClient() != null) getMenuInflater().inflate(R.menu.main, menu);
	 * 
	 * return true; }
	 * 
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { Intent
	 * clientGo = new Intent(RestaurantListActivity.this, ClientActivity.class);
	 * startActivity(clientGo); return super.onOptionsItemSelected(item); }
	 */

}
