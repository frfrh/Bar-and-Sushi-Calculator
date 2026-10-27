package de.freihube.barandsushicalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DialogChangePrice extends Activity
{
	protected static ItemFoodCountContainer foodItem;
	
	private boolean buttonPressed = false;
	
	private static EditText editTextDialogPriceUpper;
	private static EditText editTextDialogPriceLower;
	
	private static Button buttonDialogPriceUpperIncrease;
	private static Button buttonDialogPriceUpperDecrease;
	private static Button buttonDialogPriceLowerIncrease;
	private static Button buttonDialogPriceLowerDecrease;
	private static Button buttonDialogDone;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_change_price);
		
		PriceChangeHandler priceChangeHandler = new PriceChangeHandler();
		PriceWatcher priceWatcher = new PriceWatcher();
		
		editTextDialogPriceUpper =
				(EditText) findViewById(R.id.editTextDialogPriceUpper);
		editTextDialogPriceLower =
				(EditText) findViewById(R.id.EditTextDialogPriceLower);
		
		buttonDialogPriceUpperIncrease =
				(Button) findViewById(R.id.buttonDialogPriceUpperIncrease);
		buttonDialogPriceUpperDecrease =
				(Button) findViewById(R.id.buttonDialogPriceUpperDecrease);
		buttonDialogPriceLowerIncrease =
				(Button) findViewById(R.id.ButtonDialogPriceLowerIncrease);
		buttonDialogPriceLowerDecrease =
				(Button) findViewById(R.id.ButtonDialogPriceLowerDecrease);
		buttonDialogDone = (Button) findViewById(R.id.buttonDialogDone);

		editTextDialogPriceUpper.addTextChangedListener(priceWatcher);
		editTextDialogPriceLower.addTextChangedListener(priceWatcher);
		
		buttonDialogPriceUpperIncrease.setOnClickListener(priceChangeHandler);
		buttonDialogPriceUpperDecrease.setOnClickListener(priceChangeHandler);
		buttonDialogPriceLowerIncrease.setOnClickListener(priceChangeHandler);
		buttonDialogPriceLowerDecrease.setOnClickListener(priceChangeHandler);
		buttonDialogDone.setOnClickListener(priceChangeHandler);
		
		updateView();
	}
	
	private void updateView()
	{
		editTextDialogPriceUpper.setText(foodItem.getFoodItem().getPriceUpperString());
		editTextDialogPriceLower.setText(foodItem.getFoodItem().getPriceLowerString());
	}
	
	private class PriceChangeHandler implements View.OnClickListener
	{
		public void onClick(View v)
		{
			buttonPressed = true;
			switch (v.getId())
			{
				case R.id.buttonDialogPriceUpperIncrease:
					foodItem.getFoodItem().increasePriceUpper();
					break;
				case R.id.buttonDialogPriceUpperDecrease:
					foodItem.getFoodItem().decreasePriceUpper();
					break;
				case R.id.ButtonDialogPriceLowerIncrease:
					foodItem.getFoodItem().increasePriceLower();
					break;
				case R.id.ButtonDialogPriceLowerDecrease:
					foodItem.getFoodItem().decreasePriceLower();
					break;
				case R.id.buttonDialogDone:
					finish();
					break;
				default:
			}
			updateView();
		}
	}
	
	private class PriceWatcher implements TextWatcher
	{
		
		@Override
		public void afterTextChanged(Editable arg0)
		{}
		
		@Override
		public void beforeTextChanged(
				CharSequence arg0,
				int arg1,
				int arg2,
				int arg3)
		{}
		
		@Override
		public void onTextChanged(
				CharSequence s,
				int start,
				int before,
				int count)
		{
			if(buttonPressed)
			{
				buttonPressed = false;
				return;
			}
			try
			{
				foodItem.getFoodItem().setPrice(Integer.parseInt(editTextDialogPriceUpper
						.getText().toString()), Integer
						.parseInt(editTextDialogPriceLower.getText().toString()));
			} catch (NumberFormatException e)
			{
				Statics.DEBUGred("DialogChangePrice - PriceWatcher", "Input Parse Error");
			}
		}
	}
}
