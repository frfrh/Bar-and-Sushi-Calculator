package de.freihube.barandsushicalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DialogChangeCount extends Activity
{
	protected static ItemFoodCountContainer foodItem;
	
	private static EditText editTextDialogCount;
	
	private static Button buttonDialogIncreaseCount;
	private static Button buttonDialogDecreaseCount;
	private static Button buttonDialogDone;
	
	private boolean buttonPressed = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_change_count);
		
		PriceChangeHandler priceChangeHandler = new PriceChangeHandler();
		PriceWatcher priceWatcher = new PriceWatcher();
		
		editTextDialogCount =
				(EditText) findViewById(R.id.editTextDialogCount);
		
		buttonDialogIncreaseCount =
				(Button) findViewById(R.id.buttonDialogIncreaseCount);
		buttonDialogDecreaseCount =
				(Button) findViewById(R.id.buttonDialogDecreaseCount);
		buttonDialogDone = (Button) findViewById(R.id.buttonDialogDone);

		editTextDialogCount.addTextChangedListener(priceWatcher);
		
		buttonDialogIncreaseCount.setOnClickListener(priceChangeHandler);
		buttonDialogDecreaseCount.setOnClickListener(priceChangeHandler);
		buttonDialogDone.setOnClickListener(priceChangeHandler);
		
		updateView();
	}
	
	private void updateView()
	{
		editTextDialogCount.setText(Integer.toString(foodItem.getCount()));
	}
	
	private class PriceChangeHandler implements View.OnClickListener
	{
		public void onClick(View v)
		{
			buttonPressed = true;
			switch (v.getId())
			{
				case R.id.buttonDialogIncreaseCount:
					foodItem.increaseCount();
					break;
				case R.id.buttonDialogDecreaseCount:
					foodItem.decreaseCount();
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
				foodItem.setCount(Integer.parseInt(editTextDialogCount
						.getText().toString()));
			} catch (NumberFormatException e)
			{
				Statics.DEBUGred("DialogChangePrice - PriceWatcher", "Input Parse Error");
			}
		}
		
	}
}
