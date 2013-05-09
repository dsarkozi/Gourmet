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
	private ArrayList<Dish> forWork;
	private ArrayList<Groupe> list;
	private boolean fromOrder = false;
	private SubGroupe sgrp;
	private DishViewHolder dholder;
	private Dish currentdish;
	
	public DishMenuAdapter (Context c,Restaurant current, ArrayList<Dish> forWork, boolean fromOrder){
		this.context = c;
		this.forWork = forWork;
		this.current = current;
		this.fromOrder = fromOrder;
		this.inflater = LayoutInflater.from(context);
		this.list = populateType(current.getDishesTypes(forWork));
	}
	
	public void setList(ArrayList<Dish> list)
	{
		this.forWork = list;
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
		
		
	    sgrp = (SubGroupe) getChild(groupPosition, childPosition);
		boolean nat = sgrp.getWhat();
		
		if(nat){			
			if(convertView == null){
				dholder = new DishViewHolder();
				convertView = inflater.inflate(R.layout.dish_list_element, null);
				dholder.name = (TextView) convertView.findViewById(R.id.dishname);
				dholder.price = (TextView) convertView.findViewById(R.id.price);
				dholder.count = (TextView) convertView.findViewById(R.id.count);
				dholder.plus = (Button) convertView.findViewById(R.id.plus);
				dholder.minus = (Button) convertView.findViewById(R.id.minus);
				
				convertView.setTag(dholder);
			}
			else{
				dholder = (DishViewHolder) convertView.getTag();
			}
			
			dholder.price.setVisibility(View.GONE);
			dholder.count.setVisibility(View.GONE);
			dholder.name.setTextSize(18);
			
			dholder.plus.setVisibility(View.GONE);
			dholder.plus.setClickable(false);
			dholder.minus.setVisibility(View.GONE);
			dholder.minus.setClickable(false);
			
			String subtype = sgrp.getTitle();
			if(subtype != null)
				dholder.name.setText(subtype);	
			
			return convertView;
		}
		else{
			currentdish = current.getDish(sgrp.getTitle(), forWork);
		
			if(convertView == null){
				dholder = new DishViewHolder();
				convertView = inflater.inflate(R.layout.dish_list_element, null);
			
				dholder.name = (TextView) convertView.findViewById(R.id.dishname);
				dholder.price = (TextView) convertView.findViewById(R.id.price);
				dholder.count = (TextView) convertView.findViewById(R.id.count);
				dholder.plus = (Button) convertView.findViewById(R.id.plus);
				dholder.minus = (Button) convertView.findViewById(R.id.minus);
				
				convertView.setTag(dholder);
			}
			else{
				dholder = (DishViewHolder) convertView.getTag();
			}
			
			dholder.price.setVisibility(View.VISIBLE);
			dholder.count.setVisibility(View.VISIBLE);
			dholder.plus.setVisibility(View.INVISIBLE);
			dholder.minus.setVisibility(View.INVISIBLE);
			dholder.name.setTextSize(14);
			dholder.name.setText(currentdish.getName());
			dholder.price.setText(String.valueOf(currentdish.getPrice()) + " \u20ac");
			dholder.count.setText(String.valueOf(currentdish.getInventory()) + " pcs");
		
			if(fromOrder){
				dholder.plus.setVisibility(View.VISIBLE);
				dholder.plus.setClickable(true);
				dholder.minus.setVisibility(View.VISIBLE);
				dholder.minus.setClickable(true);
			
		
				dholder.plus.setOnClickListener(new OnClickListener() {
			
					@Override
					public void onClick(View v) {
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
						if(currentdish.getQuantity() > 0)
						{
							currentdish.setQuantity(currentdish.getQuantity() - 1 );
							dholder.count.setText(currentdish.getQuantity());
						}
					}
				});
			
			}
		
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
		
		SubGroupe sgrp = (SubGroupe) getChild(groupPosition, childPosition);
		return (sgrp.getWhat() == false);
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
			res.add(new Groupe(s, populateSubType(s,current.getDishesSubtypes(s, forWork))));
		}
		
		return res;
	}
	
	private ArrayList<SubGroupe> populateSubType(String type ,ArrayList<String> subtypes)
	{
		ArrayList<SubGroupe> res = new ArrayList<SubGroupe>();
		
		for(String s: subtypes){
			res.add(new SubGroupe(s,true));
			ArrayList<Dish> dishes = current.filterDishesSubtype(s, type, forWork);
			for(Dish d : dishes){
				res.add(new SubGroupe(d.getName(), false));
			}
		}
		
		
		return res;
	}
	
}
