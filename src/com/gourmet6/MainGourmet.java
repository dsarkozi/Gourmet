package com.gourmet6;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainGourmet extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		findViewById(R.id.yes_button_connection).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						login();
					}
				});
		findViewById(R.id.no_button_connection).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						//TODO
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void login(){
		setContentView(R.layout.activity_login);
	}

}
