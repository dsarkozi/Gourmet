package com.gourmet6;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;//Il me dit que la gallery n'est peut-etre plus valable...
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


//Il me dit que la gallery n'est plus valable à partir de l'API 16...
@SuppressWarnings("deprecation") 
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
		//TODO
		
		//rating bar
		ratingBar = (RatingBar) findViewById(R.id.ratingRest);
		ratingBar.setRating(currentRest.getRating());
		
		//TextView
		
		
		//Reaction du bouton de commande
		Button order = (Button) findViewById(R.id.commandeInRest);
		order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent commande = new Intent(RestaurantActivity.this, OrderActivity.class);
				commande.putExtra("from", true);
				startActivity(commande);
			}
		});
		
		//Reaction du bouton de reservation
		Button reserver = (Button) findViewById(R.id.reserveInRest);
		reserver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent reserv = new Intent(RestaurantActivity.this, ReservationActivity.class);
				startActivity(reserv);
			}
		});
		
		//Reaction du bouton menu
		Button menu = (Button) findViewById(R.id.menuInRest);
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent dishes = new Intent(RestaurantActivity.this, DishMenuActivity.class);
				startActivity(dishes);
			}
		});
		
		//Coter le restaurant
		RatingBar imp = (RatingBar) findViewById(R.id.ratingRest);
		imp.setRating(currentRest.getRating());
		imp.setOnRatingBarChangeListener(this);
	}
	
	public class ImageAdapter extends BaseAdapter {
		
		private Context mContext;

		public ImageAdapter(Context c) {
            mContext = c;
        }
		
		public int getCount() {
	        return ImageIdList.length;
	    }
	 
	    public Object getItem(int position) {
	        return position;
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
			
			i.setImageResource(ImageIdList[position]);
	        i.setLayoutParams(new Gallery.LayoutParams(150, 100));
	        i.setScaleType(ImageView.ScaleType.FIT_XY);
			
			return i;
		}
		
	}

	//liste des images TODO
	private Integer[] ImageIdList = {
	        R.drawable.ic_launcher,
	        R.drawable.ic_launcher,
	        R.drawable.ic_launcher
	};
		
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
