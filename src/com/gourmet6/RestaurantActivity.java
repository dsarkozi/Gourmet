package com.gourmet6;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;


public class RestaurantActivity extends Activity implements RatingBar.OnRatingBarChangeListener
{
	
	private Gourmet g;
	private Restaurant currentRest;
	
	private LinearLayout listImg;
	
	private RatingBar ratingBar;
	
	private TextView description;
	private TextView horaire;
	private TextView localisation;
	
	private Button order;
	private Button reserve;
	private Button menu;
	
	private boolean extended;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		// Show the Up button in the action bar.
		setupActionBar();
		
		g = (Gourmet)getApplication();
		
		currentRest = g.getRest();
		setTitle(this.currentRest.getName());
		
		//Image
		listImg = (LinearLayout) findViewById(R.id.listImgRest);
		//TODO
		
		//rating bar
		ratingBar = (RatingBar) findViewById(R.id.ratingRest);
		ratingBar.setRating(currentRest.getRating());
		//TODO viewListener ?
		//Coter le restaurant
		ratingBar.setOnRatingBarChangeListener(this);
		
		//TextView
		description = (TextView) findViewById(R.id.descriptionRest);
		description.setText(currentRest.getDescription());
		description.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				LayoutParams params = description.getLayoutParams();
				if(extended){
					params.height = 120;
					extended = false;
				}
				else{
					params.height = (currentRest.getDescription().length()*params.width)+20;
					extended = true;
				}
				description.setLayoutParams(params);
			}
		});
		
		horaire = (TextView) findViewById(R.id.horaireRest);
		//TODO affichage des villes
		
		localisation = (TextView) findViewById(R.id.localisationRest);
		localisation.setText(currentRest.getAdress()+", "+currentRest.getZip()+", "+currentRest.getTown());
		
		//Button
		//Reaction du bouton de commande
		order = (Button) findViewById(R.id.commandeInRest);
		order.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent commande = new Intent(RestaurantActivity.this, OrderActivity.class);
				commande.putExtra("from", true);
				startActivity(commande);
			}
		});
		
		//Reaction du bouton de reservation
		reserve = (Button) findViewById(R.id.reserveInRest);
		reserve.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent reserv = new Intent(RestaurantActivity.this, ReservationActivity.class);
				startActivity(reserv);
			}
		});
		
		//Reaction du bouton menu
		menu = (Button) findViewById(R.id.menuInRest);
		menu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent dishes = new Intent(RestaurantActivity.this, DishMenuActivity.class);
				startActivity(dishes);
			}
		});
	}
		
	@Override
	public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) 
	{
		//TODO
		ratingBar.setIsIndicator(true);
	}	
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
