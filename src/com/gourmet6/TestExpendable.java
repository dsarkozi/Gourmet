package com.gourmet6;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class TestExpendable extends BaseExpandableListAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Dish> list;
	
	public TestExpendable (Context c, ArrayList<Dish> list){
		this.context = c;
		this.inflater = LayoutInflater.from(context);
		this.list = list;	
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition).getAllergens().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupViewHolder gholder;
		
		if(convertView == null)
		{
			gholder = new GroupViewHolder();
			convertView = inflater.inflate(R.layout.grp_layout, null);
			gholder.textViewGroup = (TextView) convertView.findViewById(R.id.groupe); 
			convertView.setTag(gholder);
		}
		else{
			gholder = (GroupViewHolder) convertView.getTag();
		}
		
		String aller = (String)getChild(groupPosition, childPosition);
		
		if(aller != null)
			gholder.textViewGroup.setText(aller);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		ArrayList<String> aller = list.get(groupPosition).getAllergens();
		if (aller != null)
		{
			return aller.size();
		}
		else
			return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		GroupViewHolder gholder;
		
		Dish dish = (Dish) getGroup(groupPosition);
		
		if(convertView == null)
		{
			gholder = new GroupViewHolder();
			convertView = inflater.inflate(R.layout.grp_layout, null);
			gholder.textViewGroup = (TextView) convertView.findViewById(R.id.groupe); 
			convertView.setTag(gholder);
		}
		else{
			gholder = (GroupViewHolder) convertView.getTag();
		}
		String name = dish.getName();
		gholder.textViewGroup.setText(name);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	class GroupViewHolder {
	      public TextView textViewGroup;
	}

}
