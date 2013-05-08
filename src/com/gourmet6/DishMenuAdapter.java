package com.gourmet6;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DishMenuAdapter extends BaseExpandableListAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private Restaurant current;
	private ArrayList<Groupe> list;
	private boolean fromOrder = false;
	
	public DishMenuAdapter (Context c, Restaurant current, boolean fromOrder){
		this.context = c;
		this.current = current;
		this.fromOrder = fromOrder;
		this.inflater = LayoutInflater.from(context);
		this.list = populateType(current.getDishesTypes());
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return list.get(groupPosition).getSub().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,boolean isLastChild, View convertView, ViewGroup parent) {
		final DishViewHolder dholder;
		
		final SubGroupe sgrp = (SubGroupe) getChild(groupPosition, childPosition);
		boolean nat = sgrp.getWhat();
		
		if(nat){
			if(convertView == null){
				dholder = new DishViewHolder();
				convertView = inflater.inflate(R.layout.child_layout, null);
				dholder.name = (TextView) convertView.findViewById(R.id.child);
			}
			else{
				dholder = (DishViewHolder) convertView.getTag();
			}
			
			dholder.name.setText(sgrp.getTitle());	
			return convertView;
		}
		else{
			final Dish currentdish = current.getDish(sgrp.getTitle());
		
			if(convertView == null){
				dholder = new DishViewHolder();
				convertView = inflater.inflate(R.layout.dish_list_element, null);
			
				dholder.name = (TextView) convertView.findViewById(R.id.dishname);
				dholder.price = (TextView) convertView.findViewById(R.id.price);
				dholder.count = (TextView) convertView.findViewById(R.id.count);
				dholder.plus = (Button) convertView.findViewById(R.id.plus);
				dholder.minus = (Button) convertView.findViewById(R.id.minus);		
			}
			else{
				dholder = (DishViewHolder) convertView.getTag();
			}
		
			dholder.name.setText(currentdish.getName());
			dholder.price.setText(String.valueOf(currentdish.getPrice()));
			dholder.count.setText("Stock:"+String.valueOf(currentdish.getInventory()));
		
			if(fromOrder){
				dholder.plus.setVisibility(View.VISIBLE);
				dholder.plus.setClickable(true);
				dholder.minus.setVisibility(View.VISIBLE);
				dholder.minus.setClickable(true);
			}
		
			dholder.plus.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(currentdish.getQuantity() < currentdish.getInventory())
					{
						currentdish.setQuantity(currentdish.getQuantity() + 1 );
						dholder.count.setText(currentdish.getQuantity());
					}
				}
			});
		
			dholder.plus.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(currentdish.getQuantity() > 0)
					{
						currentdish.setQuantity(currentdish.getQuantity() - 1 );
						dholder.count.setText(currentdish.getQuantity());
					}
				}
			});
		
			return convertView;
		}
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return list.get(groupPosition).getSub().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		
		return list.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		
		return list.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		GroupeViewHolder gholder;
		
		Groupe group = (Groupe) getGroup(groupPosition);
		
		if(convertView == null)
		{
			gholder = new GroupeViewHolder();
			convertView = inflater.inflate(R.layout.grp_layout, null);
			gholder.type = (TextView) convertView.findViewById(R.id.groupe);
			convertView.setTag(gholder);
		}
		else{
			gholder = (GroupeViewHolder) convertView.getTag();
		}
		
		gholder.type.setText(group.getType());
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		
		return true;
	}
	
	private class GroupeViewHolder{
		public TextView type;
	}
	
	private class DishViewHolder{
		public TextView name;
		public TextView price;
		public Button plus;
		public TextView count;
		public Button minus;
	}
	
	private class Groupe{
		private String type;
		private ArrayList<SubGroupe> sublist;
		
		public Groupe(String type, ArrayList<SubGroupe> subt)
		{
			this.type = type;
			this.sublist = subt;
		}
		
		public String getType()
		{
			return type;
		}

		public ArrayList<SubGroupe> getSub() 
		{
			return sublist;
		}
	
	}
	
	private class SubGroupe{
		private boolean what;
		private String title;
		
		public SubGroupe(String name, boolean itis)
		{
			this.title = name;
			this.what = itis;
		}
		
		public boolean getWhat() {
			return what;
		}

		public String getTitle() {
			return title;
		}
	}
	
	private ArrayList<Groupe> populateType(ArrayList<String> types)
	{
		ArrayList<Groupe> res = new ArrayList<Groupe>(types.size());
		
		for(String s : types){
			res.add(new Groupe(s, populateSubType(s, current.getDishesSubtypes(s))));
		}
		
		return res;
	}
	
	private ArrayList<SubGroupe> populateSubType(String type ,ArrayList<String> subtypes)
	{
		ArrayList<SubGroupe> res = new ArrayList<SubGroupe>();
		
		for(String s: subtypes){
			res.add(new SubGroupe(s,true));
			ArrayList<Dish> dishes = current.filterDishesSubtype(s, type);
			for(Dish d : dishes){
				res.add(new SubGroupe(d.getName(), false));
			}
		}
		
		return res;
	}

}
