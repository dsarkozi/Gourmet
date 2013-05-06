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
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


//Il me dit que la gallery n'est plus valable à partir de l'API 16...
@SuppressWarnings("deprecation") 
public class RestaurantActivity extends Activity implements RatingBar.OnRatingBarChangeListener {
	
	Gourmet g = (Gourmet)getApplication();
	private Restaurant current = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);
		// Show the Up button in the action bar.
		setupActionBar();
		
		current = g.getRest();
		setTitle(this.current.getName());
		
		//TextView
		TextView description = (TextView) findViewById(R.id.textView1);
		description.setText(current.getDescription());
		
		TextView localisation = (TextView) findViewById(R.id.textView2);
		localisation.setText(current.getAdress());
		
		TextView numrating = (TextView) findViewById(R.id.textView3);
		numrating.setText(current.getNbrPrsHasVoted());
		
		//Reaction du bouton de commande
		Button order = (Button) findViewById(R.id.button3);
		order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent commande = new Intent(RestaurantActivity.this, OrderActivity.class);
				commande.putExtra("from", true);
				startActivity(commande);
			}
		});
		
		//Reaction du bouton de reservation
		Button reserver = (Button) findViewById(R.id.button2);
		reserver.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent reserv = new Intent(RestaurantActivity.this, ReservationActivity.class);
				startActivity(reserv);
			}
		});
		
		//Reaction du bouton menu
		Button menu = (Button) findViewById(R.id.button1);
		menu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent dishes = new Intent(RestaurantActivity.this, DishMenuActivity.class);
				startActivity(dishes);
			}
		});
		
		//Coter le restaurant
		RatingBar imp = (RatingBar) findViewById(R.id.ratingBar1);
		imp.setRating(current.getRating());
		imp.setOnRatingBarChangeListener(this);
		
		
		//Gallery  d'images
		Gallery envy = (Gallery) findViewById(R.id.gallery1);
		envy.setAdapter(new ImageAdapter(this));
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
