package de.freihube.barandsushicalculator;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;


public class AdapterForLocation extends ArrayAdapter<ItemFoodCheckContainer>
{
	Context context;
	ArrayList<ItemFoodCheckContainer> data = null;
	
	public AdapterForLocation(Activity activity, ArrayList<ItemFoodCheckContainer> data)
	{
		super(activity, R.layout.listitem_bill, data);
		this.context = activity;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		ViewHolder holder = null;
		
		ItemFoodCheckContainer itemView = data.get(position);
		if (row == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.listitem_location, parent, false);
			
			holder = new ViewHolder();
			
			holder.checkBoxSelected = (CheckBox) row.findViewById(R.id.checkBoxSelected);
			
			holder.textViewName =
					(TextView) row.findViewById(R.id.textViewName);
			holder.textViewPrice =
					(TextView) row.findViewById(R.id.textViewPrice);
			
			holder.imageIcon = (ImageView) row.findViewById(R.id.imageViewIcon);
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.checkBoxSelected.setOnCheckedChangeListener(new SelectListener(itemView));
		
		holder.checkBoxSelected.setChecked(itemView.isChecked());
		holder.textViewName.setText(itemView.getFoodItem().getName());
		holder.textViewPrice.setText(itemView.getFoodItem().getPriceString());
		holder.imageIcon.setImageResource(itemView.getFoodItem().getIconID());
		
		return row;
	}
	
	static class ViewHolder
	{
		ImageView imageIcon;
		TextView textViewName, textViewPrice;
		CheckBox checkBoxSelected;
	}
	
	private class SelectListener implements OnCheckedChangeListener
	{
		private ItemFoodCheckContainer itemView;
		
		public SelectListener(ItemFoodCheckContainer foodContainer)
		{
			this.itemView = foodContainer;
		}
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		{
			itemView.setChecked(isChecked);
		}
	}
}
