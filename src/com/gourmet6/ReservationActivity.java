package com.gourmet6;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class ReservationActivity extends Activity {
	
	private int people;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	
	private String s;
	private TextView dateTime;
	private EditText nbrPrs;
	
	private Gourmet g ;
	
	private Dialog dialog;
	private Dialog message;
	
	private Context context;
	
	private Restaurant current;
	private boolean from = false;
	
	private DBHandler dbh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, R.anim.commetuveux);
		setContentView(R.layout.activity_reservation);
		setTitle(R.string.activity_reservation_title);
		
		g = (Gourmet)getApplication();
		context = this;
		dbh = new DBHandler(context);
		
		Bundle extra = getIntent().getExtras();
		this.from = extra.getBoolean("fromOrder");
		
		final Calendar c = Calendar.getInstance();
		c.setTimeZone(TimeZone.getDefault());
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		nbrPrs = (EditText) findViewById(R.id.nbrPrsReserv);
		
		dateTime = (TextView) findViewById(R.id.dateTime);
		s = year+"-"+month+"-"+day+" "+hour+":"+minute;
		dateTime.setText(s);
		dateTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog = new Dialog(context);
				dialog.setContentView(R.layout.datetimedialog);
				dialog.setTitle("Custom Dialog");
				
				final DatePicker dp = (DatePicker) dialog.findViewById(R.id.datePicker1);
				dp.init(year, month, day, null);
				
				final TimePicker tp = (TimePicker) dialog.findViewById(R.id.timePicker1);
				tp.setIs24HourView(true);
				tp.setCurrentHour(hour);
				tp.setCurrentMinute(0);
				
				Button ok = (Button) dialog.findViewById(R.id.buttonOkDialog);
				ok.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						year = dp.getYear();
						month = dp.getMonth();
						day = dp.getDayOfMonth();
						hour = tp.getCurrentHour();
						minute = tp.getCurrentMinute();
						
						GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute);
						s = TimeTable.parseDateInString(calendar);
						dateTime.setText(s);
						
						dialog.cancel();
					}
				});
				
				Button cancel = (Button) dialog.findViewById(R.id.buttonCancelDialog);
				cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.cancel();
					}
				});
				
				dialog.show();
			}
		});
		
		//Reaction du bouton de commande
		Button order = (Button) findViewById(R.id.comInReserv);
		if(from){
			order.setEnabled(false);
		}else{
			order.setEnabled(true);
		}
		order.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//TODO create the reservation object
				if(checkReservation()){
					current = g.getRest();
					current.createListDishes(new DBHandler(ReservationActivity.this));
					Intent commande = new Intent(ReservationActivity.this, OrderActivity.class);
					commande.putExtra("from", false);
					startActivity(commande);
				}
			}
		});	
		
		//Reaction du bouton de soumission
		Button submit = (Button) findViewById(R.id.validateReserv);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO suppression reservation ?
				if(checkReservation()){
					Intent commande = new Intent(ReservationActivity.this, RestaurantActivity.class);
					startActivity(commande);
				}
			}
		});
		
	}
	
	public boolean checkReservation()
	{
		people = Integer.parseInt(nbrPrs.getText().toString());
		s = year+"-"+(month+1)+"-"+day+" "+hour+":"+minute;
		Reservation reservTemp = new Reservation(g.getRest().getName(), s, people, g.getClient().getName(), g.getClient().getEmail());
		String res = g.getRest().checkReservation(reservTemp, dbh);
		if(res!=null)
		{
			message = new Dialog(context);
			message.setContentView(R.layout.message_dialog);
			message.setTitle("Erreur");
			
			TextView tv = (TextView) message.findViewById(R.id.textViewMessageDialog);
			tv.setText(res);
			
			Button b = (Button) message.findViewById(R.id.okButtonMessageDialog);
			b.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					message.cancel();
				}
			});
			
			message.show();
			
			return false;
		}
		else
		{
			return true;
		}
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
		getMenuInflater().inflate(R.menu.reservation, menu);
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
