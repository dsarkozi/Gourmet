package com.gourmet6;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;


public class RestaurantActivity extends Activity
{
	
	private Gourmet g;
	private Restaurant currentRest;
	
	private LinearLayout listImg;
	
	private RatingBar ratingBar;
	private RatingBar toRateBar;
	
	private TextView description;
	private TextView horaire;
	private TextView localisation;
	private TextView tvRate;
	
	private Button order;
	private Button reserve;
	private Button menu;
	
	private boolean extended;
	private boolean hasRated;
	
	private Dialog dialog;
	
	private DBHandler dbh;
	
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		overridePendingTransition(0, R.anim.commetuveux);
		// Show the Up button in the action bar.
		setupActionBar();
		
		g = (Gourmet)getApplication();
		context = this;
		dbh = new DBHandler(context);
		
		hasRated = false;
		
		//currentRest = g.getRest();
		/*currentRest = new Restaurant("test", "test", "rue de Scoumont", "Rosseignies", "0478625", 
				"Ceci est un super restaurant super cool avec plein de plats et un menu hyper varié et je me "
				+"casse pas le cul, patatititititi patatatatatatataat", 4.0, 5, 6230, 48, 20, 8.544, 9.63, 9.62);*/
		setTitle(this.currentRest.getName());
		
		//Image
		listImg = (LinearLayout) findViewById(R.id.listImgRest);
		addImage();
		
		//rating bar
		ratingBar = (RatingBar) findViewById(R.id.ratingRest);
		ratingBar.setRating(currentRest.getRating());
		ratingBar.setIsIndicator(true);
		ratingBar.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(!hasRated)
				{
					createDialogToRate();
				}
				return false;
			}
		});
		
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
		setHorair();
		
		localisation = (TextView) findViewById(R.id.localisationRest);
		setLocalisation();
		
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
				currentRest.createListDishes(new DBHandler(RestaurantActivity.this));
				Intent dishes = new Intent(RestaurantActivity.this, DishMenuActivity.class);
				startActivity(dishes);
			}
		});
	}
	
	private void addImage()
	{
		// TODO Auto-generated method stub
		ImageView img = new ImageView(context);
		img.setImageResource(R.drawable.ic_launcher);
		listImg.addView(img);
	}

	private void setLocalisation()
	{
		String res = "";
		if(currentRest.getAdress()!=null){
			res = res+currentRest.getAdress()+", ";
		}
		if(currentRest.getZip()!=0){
			res = res+currentRest.getZip()+", ";
		}
		if(currentRest.getTown()!=null){
			res = res+ currentRest.getTown();
		}
		localisation.setText(res);
	}

	private void setHorair()
	{
		// TODO A la ligne ?
		System.out.println("Hey");
		String s=""; String oldStartDay=null; String oldEndDay = null;
		System.out.println(currentRest.getSemaine());
		if(currentRest.getSemaine()==null) return;
		for(TimeTable tt : currentRest.getSemaine())
		{
			System.out.println("kikoo");
			if(tt.getJourDebut().equals(tt.getJourFin()))
			{
				if(oldStartDay==null && oldEndDay==null)
				{
					s = s+tt.parseInString()+"\n";
				}
			}
		}
		horaire.setText(s);
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
	
	public void createDialogToRate()
	{
		dialog = new Dialog(context);
		dialog.setContentView(R.layout.rating_dialog);
		dialog.setTitle("Rate restaurant");
		
		tvRate = (TextView) dialog.findViewById(R.id.typeCotation);
		lookForType((int)ratingBar.getRating());
		
		toRateBar = (RatingBar) dialog.findViewById(R.id.ratingBarToRate);
		toRateBar.setRating(ratingBar.getRating());
		toRateBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				System.out.println(rating);
				lookForType((int)rating);
			}
		});
		
		Button okButtonRate = (Button) dialog.findViewById(R.id.okButtonRate);
		okButtonRate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				currentRest.rateRestaurant(toRateBar.getRating(), dbh);
				hasRated = true;
				ratingBar.setRating(currentRest.getRating());
				dialog.cancel();
			}
		});
		
		Button cancelButtonRate = (Button) dialog.findViewById(R.id.cancelButtonRate);
		cancelButtonRate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
		
		dialog.show();
	}
	
	public void lookForType(int rating)
	{
		switch(rating)
		{
		case 0: 
			tvRate.setText("Mediocre"); 
			break;
		case 1: 
			tvRate.setText("Mauvais"); 
			break;
		case 2: 
			tvRate.setText("Moyen");
			break;
		case 3: 
			tvRate.setText("Bon");
			break;
		case 4: 
			tvRate.setText("Tres bon");
			break;
		case 5: 
			tvRate.setText("Excellent");
			break;
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
