package de.freihube.barandsushicalculator;

import java.io.Serializable;
import java.util.ArrayList;

import de.freihube.barandsushicalculator.R;
import android.app.Activity;
import android.content.Context;
import android.view.*;
import android.widget.*;


public class AdapterForBill extends ArrayAdapter<ItemFoodCountContainer>
{
	Context context;
	ArrayList<ItemFoodCountContainer> data = null;
	FoodListActivity callingActivity;
	
	public AdapterForBill(FoodListActivity callingActivity, ArrayList<ItemFoodCountContainer> data)
	{
		super(callingActivity, R.layout.listitem_bill, data);
		this.context = callingActivity;
		this.callingActivity = callingActivity;
		this.data = data;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View row = convertView;
		ViewHolder holder = null;
		
		ItemFoodCountContainer itemView = data.get(position);
		if (row == null)
		{
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(R.layout.listitem_bill, parent, false);
			
			holder = new ViewHolder();
			
			holder.textViewName =
					(TextView) row.findViewById(R.id.textViewName);
			holder.textViewPrice =
					(TextView) row.findViewById(R.id.textViewPrice);
			holder.textViewTotalPrice =
					(TextView) row.findViewById(R.id.textViewTotalPrice);
			
			holder.editTextCount =
					(TextView) row.findViewById(R.id.editTextCount);
			
			holder.imageDecrease =
					(ImageView) row.findViewById(R.id.imageViewDecrease);
			holder.imageIcon = (ImageView) row.findViewById(R.id.imageViewIcon);
			holder.imageIncrease =
					(ImageView) row.findViewById(R.id.imageViewIncrease);
			
			row.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) row.getTag();
		}
		
		holder.textViewName.setText(itemView.getFoodItem().getName());
		holder.textViewPrice.setText(itemView.getFoodItem().getPriceString());
		holder.textViewTotalPrice.setText(itemView.getTotalPriceString());
		
		holder.editTextCount.setText("" + itemView.getCount());
		
		holder.imageDecrease.setImageResource(R.drawable.button_decrease);
		holder.imageIcon.setImageResource(itemView.getFoodItem().getIconID());
		holder.imageIncrease.setImageResource(R.drawable.button_increase);
		
		holder.imageDecrease.setOnClickListener(new DecreaseListener(itemView, callingActivity));
		holder.imageIncrease.setOnClickListener(new IncreaseListener(itemView, callingActivity));
		
		return row;
	}
	
	static class ViewHolder
	{
		ImageView imageIcon, imageDecrease, imageIncrease;
		TextView textViewName, textViewPrice, textViewTotalPrice;
		TextView editTextCount;
	}
	private class IncreaseListener implements View.OnClickListener, Serializable
	{
		private static final long serialVersionUID = -5297043740028827007L;
		private ItemFoodCountContainer itemView;
		private FoodListActivity callingActivity;
		
		public IncreaseListener(ItemFoodCountContainer itemView,
				FoodListActivity callingActivity)
		{
			super();
			this.itemView = itemView;
			this.callingActivity = callingActivity;
		}
		
		@Override
		public void onClick(View v)
		{
			itemView.increaseCount();
			this.callingActivity.updateFoodList(true);
		}
	}
	
	private class DecreaseListener implements View.OnClickListener, Serializable
	{
		private static final long serialVersionUID = 4610050997889060228L;
		private ItemFoodCountContainer itemView;
		private FoodListActivity callingActivity;
		
		public DecreaseListener(ItemFoodCountContainer itemView,
				FoodListActivity callingActivity)
		{
			super();
			this.itemView = itemView;
			this.callingActivity = callingActivity;
		}
		
		@Override
		public void onClick(View v)
		{
			Statics.DEBUGblue("ItemFoodList - DecreaseListener",
					"clicked decrease");
			if (itemView.getCount() <= 0)
				itemView.setCount(0);
			else
				itemView.decreaseCount();
			this.callingActivity.updateFoodList(true);
		}
	}
}
