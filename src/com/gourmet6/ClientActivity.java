package com.gourmet6;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class ClientActivity extends Activity {

	
	private Gourmet g = (Gourmet)getApplication();
	private Client currentCli;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		
		currentCli = g.getClient();
		
		//nom du client + son mail sont affiché au dessus
		TextView nameCli = (TextView) findViewById(R.id.name);
	    nameCli.setText(currentCli.getName());
		TextView mailCli = (TextView) findViewById(R.id.mail);
	    mailCli.setText(currentCli.getEmail());
		
		
		Button btn;
		//récupération du bouton des réservations et commandes
        btn = (Button)findViewById(R.id.rescom);
        //écouteur sur le clic du bouton
        btn.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {

        		Intent resAndCom = new Intent(ClientActivity.this, ResAndComActivity.class);
				resAndCom.putExtra("from", true);
				startActivity(resAndCom);
        	}
        });
        
        Button btn2;
        
        //récupération du bouton des préférences
        btn2 = (Button)findViewById(R.id.pref);
        //écouteur sur le clic du bouton
        btn2.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {

        		Intent preferences = new Intent(ClientActivity.this, PrefActivity.class);
        		preferences.putExtra("from", true);
				startActivity(preferences);
         	}
        });
        
        Button btn3;
        //récupération du bouton des préférences
        btn3 = (Button)findViewById(R.id.cpd);
        //écouteur sur le clic du bouton
        btn3.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {

        		Intent newPersData = new Intent(ClientActivity.this, NewPersDataActivity.class);
        		newPersData.putExtra("from", true);
				startActivity(newPersData);
         	}
        });

		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.client, menu);
		return true;
	}

}
