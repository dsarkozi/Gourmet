package com.gourmet6;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;


public class ResAndComActivity extends Activity {

	private Gourmet g = (Gourmet)getApplication();
	private Client currentCli;
	private ExpandableListView expandableList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_res_and_com);
		
		currentCli = g.getClient();
		
		// ArrayList<Reservation> myRes;  utiliser méthode Lena permettant de récupérer les résevations d'un Client depuis la DB
		
		/*
		 * expandableList = (ExpandableListView) findViewById(R.id.expandableListView1);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 */
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.res_and_com, menu);
		return true;
	}
	
	
	public class ResAndComAdapter extends BaseExpandableListAdapter{

		@Override
		public Object getChild(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getChildId(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getChildView(int arg0, int arg1, boolean arg2, View arg3,
				ViewGroup arg4) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getChildrenCount(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getGroup(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getGroupCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long getGroupId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}


