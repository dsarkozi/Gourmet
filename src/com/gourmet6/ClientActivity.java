package com.gourmet6;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientActivity extends Activity
{

	private Gourmet g;
	private Client currentCli;
	private static final String TITLE = "Profile";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		setTitle(TITLE);
		setupActionBar();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		g = (Gourmet) getApplication();
		currentCli = g.getClient();

		// nom du client + son mail sont affich� au dessus
		TextView nameCli = (TextView) findViewById(R.id.name);
		nameCli.setText("Name : " + currentCli.getName());

		TextView mailCli = (TextView) findViewById(R.id.mail);
		mailCli.setText("Email : " + currentCli.getEmail());

		TextView phoneCli = (TextView) findViewById(R.id.phone);
		phoneCli.setText("Phone : " + currentCli.getPhone());

		// r�cup�ration du bouton des pr�f�rences
		Button btn3 = (Button) findViewById(R.id.cpd);
		// �couteur sur le clic du bouton
		btn3.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				Intent newPersData = new Intent(ClientActivity.this,
						NewPersDataActivity.class);
				startActivity(newPersData);
			}
		});
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
}
