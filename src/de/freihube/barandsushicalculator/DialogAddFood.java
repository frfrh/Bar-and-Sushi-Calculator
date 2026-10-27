package de.freihube.barandsushicalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import de.freihube.barandsushicalculator.ItemFoodList.FoodType;

public class DialogAddFood extends Activity
{
	protected static ItemFoodList foodItem;
	
	private Activity activity;
	
	private boolean buttonPressed = false;
	
	private static EditText editTextNameNew;
	private static EditText editTextDialogPriceUpper;
	private static EditText editTextDialogPriceLower;
	
	private static ImageButton imageButtonIconNew;
	
	private static Button buttonDialogPriceUpperIncrease;
	private static Button buttonDialogPriceUpperDecrease;
	private static Button buttonDialogPriceLowerIncrease;
	private static Button buttonDialogPriceLowerDecrease;
	private static Button buttonDialogAdd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_add_food);
		setResult(Activity.RESULT_CANCELED);
		
		activity=this;
		
		foodItem = new ItemFoodList("", FoodType.FOOD, 0, R.drawable.plate_blue);

		PriceChangeHandler priceChangeHandler = new PriceChangeHandler();
		PriceWatcher priceWatcher = new PriceWatcher();
		NameWatcher nameWatcher = new NameWatcher();
		
		editTextNameNew = (EditText) findViewById(R.id.editTextNameNew);
		editTextDialogPriceUpper = (EditText) findViewById(R.id.editTextDialogPriceUpper);
		editTextDialogPriceLower = (EditText) findViewById(R.id.EditTextDialogPriceLower);
		
		imageButtonIconNew = (ImageButton) findViewById(R.id.imageButtonIconNew);
		
		buttonDialogPriceUpperIncrease = (Button) findViewById(R.id.buttonDialogPriceUpperIncrease);
		buttonDialogPriceUpperDecrease = (Button) findViewById(R.id.buttonDialogPriceUpperDecrease);
		buttonDialogPriceLowerIncrease = (Button) findViewById(R.id.ButtonDialogPriceLowerIncrease);
		buttonDialogPriceLowerDecrease = (Button) findViewById(R.id.ButtonDialogPriceLowerDecrease);
		buttonDialogAdd = (Button) findViewById(R.id.buttonDialogAdd);
		
		editTextNameNew.addTextChangedListener(nameWatcher);
		
		editTextDialogPriceUpper.addTextChangedListener(priceWatcher);
		editTextDialogPriceLower.addTextChangedListener(priceWatcher);
		
		buttonDialogPriceUpperIncrease.setOnClickListener(priceChangeHandler);
		buttonDialogPriceUpperDecrease.setOnClickListener(priceChangeHandler);
		buttonDialogPriceLowerIncrease.setOnClickListener(priceChangeHandler);
		buttonDialogPriceLowerDecrease.setOnClickListener(priceChangeHandler);
		buttonDialogAdd.setOnClickListener(priceChangeHandler);
		
		imageButtonIconNew.setOnClickListener(priceChangeHandler);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		updateView();
	}
	
	private void updateView()
	{
		editTextDialogPriceUpper.setText(foodItem.getPriceUpperString());
		editTextDialogPriceLower.setText(foodItem.getPriceLowerString());
		imageButtonIconNew.setImageResource(foodItem.getIconID());
	}
	
	private class PriceChangeHandler implements View.OnClickListener
	{
		public void onClick(View v)
		{
			buttonPressed = true;
			Intent intent;
			switch (v.getId())
			{
				case R.id.buttonDialogPriceUpperIncrease:
					foodItem.increasePriceUpper();
					break;
				case R.id.buttonDialogPriceUpperDecrease:
					foodItem.decreasePriceUpper();
					break;
				case R.id.ButtonDialogPriceLowerIncrease:
					foodItem.increasePriceLower();
					break;
				case R.id.ButtonDialogPriceLowerDecrease:
					foodItem.decreasePriceLower();
					break;
				case R.id.imageButtonIconNew:
					intent = new Intent(activity, DialogChooseIcon.class);
					startActivityForResult(intent, Statics.REQUEST_CODE_CHOOSE_ICON);
					break;
				case R.id.buttonDialogAdd:
					intent = new Intent();
					intent.putExtra(IntentExtra.KEY_ITEM_FOOD_LIST, foodItem);
					setResult(Activity.RESULT_OK, intent);
					finish();
					break;
				default:
			}
			updateView();
		}
	}
	
	@Override
	protected void
			onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == Statics.REQUEST_CODE_CHOOSE_ICON)
		{
			if (resultCode == RESULT_OK)
			{
				int result = data.getIntExtra(IntentExtra.KEY_ICON_ID, R.drawable.plate_blue);
				foodItem.setIconID(result);
				updateView();
			}
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
			if (buttonPressed)
			{
				buttonPressed = false;
				return;
			}
			try
			{
				foodItem.setPrice(Integer.parseInt(editTextDialogPriceUpper
						.getText().toString()),
						Integer.parseInt(editTextDialogPriceLower.getText()
								.toString()));
			} catch (NumberFormatException e)
			{
				Statics.DEBUGred("DialogChangePrice - PriceWatcher",
						"Input Parse Error");
			}
		}
	}
	
	private class NameWatcher implements TextWatcher
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
			
				foodItem.setName(s.toString());
			
		}
	}
}
