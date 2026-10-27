package de.freihube.barandsushicalculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;
import de.freihube.barandsushicalculator.IntentExtra.IntentExtraType;
import de.freihube.barandsushicalculator.ItemFoodList.FoodType;


public class ActivityLocation extends FoodListActivity
{
	public static ActivityLocation activityLocation;
	private static ArrayList<ItemFoodCheckContainer> foodItems =
			new ArrayList<ItemFoodCheckContainer>();
	private static TouchInterceptor listViewFood;
	
	private static Handler handler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Statics.DEBUGred("Bar and Sushi Calculator",
				"+++ ON CREATE Location +++");
		super.onCreate(savedInstanceState);
		activityLocation = this;
		setContentView(R.layout.activity_location);
		
		listViewFood =
				(TouchInterceptor) findViewById(R.id.listViewFoodLocation);
		listViewFood.setDropListener(new LocationDropListener());
		
		ActivityLocation.loadLocation(Statics.FILE_NAME_CURRENT);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Statics.DEBUGred("Bar and Sushi Calculator", "++ ON RESUME LOCATION ++");
		if ((foodItems == null || foodItems.size() == 0)
				&& Statics.DEBUG_FILL_LOCATION)
		{
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_black_plate), FoodType.FOOD,
					1, 05, R.drawable.plate_black));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_blue_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_blue));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_green_plate), FoodType.FOOD,
					1, 05, R.drawable.plate_green));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_grey_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_grey));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_orange_plate), FoodType.FOOD,
					1, 05, R.drawable.plate_orange));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_pink_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_pink));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_red_plate), FoodType.FOOD, 1,
					05, R.drawable.plate_red));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_white_plate), FoodType.FOOD,
					1, 05, R.drawable.plate_white));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_yellow_plate), FoodType.FOOD,
					1, 05, R.drawable.plate_yellow));
			foodItems.add(new ItemFoodCheckContainer(
					getString(R.string.text_name_purple_plate), FoodType.FOOD,
					1, 05, R.drawable.plate_purple));
		}
		updateList(false);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		saveLocation(foodItems, Statics.FILE_NAME_CURRENT);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu_activity_location, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle item selection
		switch (item.getItemId())
		{
			case R.id.action_add_food:
				Statics.DEBUGgreen("ActivityLocation - onOptionsItemSelected",
						"add_food");
				addFood();
				return true;
			case R.id.action_add_to_bill:
				addFoodToBill();
				return true;
			case R.id.action_delete_selected:
				deleteSelected();
				return true;
			case R.id.action_save_location:
				Statics.DEBUGgreen("ActivityLocation - onOptionsItemSelected",
						"save_location");
				saveLocation();
				return true;
			case R.id.action_load_location:
				Statics.DEBUGgreen("ActivityLocation - onOptionsItemSelected",
						"load_location");
				loadLocation();
				return true;
			case R.id.action_clear_location:
				Statics.DEBUGgreen("ActivityLocation - onOptionsItemSelected",
						"clearlocation");
				clearLocation();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	protected void
			onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == Statics.REQUEST_CODE_FILENAME_LOCATION)
		{
			if (resultCode == RESULT_OK)
			{
				IntentExtra result =
						(IntentExtra) data
								.getSerializableExtra(IntentExtra.KEY_INTENT_EXTRA);
				if (result != null)
					loadLocation(result.getMessage());
			}
		}
		if (requestCode == Statics.REQUEST_CODE_ADD_FOOD)
		{
			if (resultCode == RESULT_OK)
			{
				ItemFoodList result =
						(ItemFoodList) data
								.getSerializableExtra(IntentExtra.KEY_ITEM_FOOD_LIST);
				foodItems.add(new ItemFoodCheckContainer(result));
				updateList(false);
				
			}
		}
	}
	
	public void updateFoodList(boolean keepScroll)
	{
		updateList(keepScroll);
	}
	
	public static void updateList(boolean keepScroll)
	{
		if (keepScroll)
		{
			int currentVisiblePosition = listViewFood.getFirstVisiblePosition();
			View v = listViewFood.getChildAt(currentVisiblePosition);
			int currentYPosition = (v == null) ? 0 : v.getTop();
			
			listViewFood.setAdapter(new AdapterForLocation(
					((activityLocation != null)
							? activityLocation
							: Statics.activity), foodItems));
			if (currentVisiblePosition > 0)
				currentVisiblePosition++;
			listViewFood.setSelectionFromTop(currentVisiblePosition,
					currentYPosition);
		}
		else
		{
			AdapterForLocation adapter =
					new AdapterForLocation(activityLocation, foodItems);
			listViewFood.setAdapter(adapter);
		}
	}
	
	private void addFood()
	{
		Intent intent = new Intent(this, DialogAddFood.class);
		startActivityForResult(intent, Statics.REQUEST_CODE_ADD_FOOD);
	}
	
	private void addFoodToBill()
	{
		boolean anythingSelected = false;
		for (ItemFoodCheckContainer foodItem : foodItems)
		{
			if (foodItem.isChecked())
			{
				anythingSelected = true;
				ActivityBill.addItemToFoodList(foodItem.getFoodItem());
			}
		}
		if (!anythingSelected)
		{
			try
			{
				Toast.makeText(getApplicationContext(),
						R.string.error_nothing_selected_in_location,
						Toast.LENGTH_LONG).show();
			} catch (Exception e)
			{}
		}
	}
	
	private boolean deleteSelected()
	{
		new AlertDialog.Builder(this)
				.setMessage(getResources()
						.getString(R.string.questionmark_leading)
						+ getResources()
								.getString(R.string.text_sure_delete_selection)
						+ getResources().getString(R.string.questionmark))
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						for (int i = 0; i < foodItems.size(); i++)
						{
							if (foodItems.get(i).isChecked())
							{
								foodItems.remove(i);
								i--;
							}
						}
						updateList(true);
					}
				}).setNegativeButton("No", null).show();
		return true;
	}
	
	private void clearLocation()
	{
		new AlertDialog.Builder(this)
				.setMessage(getResources()
						.getString(R.string.questionmark_leading)
						+ getResources()
								.getString(R.string.text_sure_clear_location)
						+ getResources().getString(R.string.questionmark))
				.setCancelable(false)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						foodItems.clear();
						updateList(false);
					}
				}).setNegativeButton("No", null).show();
	}
	
	private void loadLocation()
	{
		Intent intent = new Intent(this, DialogLoadFile.class);
		intent.putExtra(IntentExtra.KEY_INTENT_EXTRA, new IntentExtra(
				IntentExtraType.CHOOSE_LOCATION_FILENAME));
		startActivityForResult(intent, Statics.REQUEST_CODE_FILENAME_LOCATION);
	}
	
	protected static void loadLocation(String filename)
	{
		Statics.DEBUGgreen("ActivityLocation - loadLocation", "load from: "
				+ filename);
		if (filename.equals(""))
		{
			return;
		}
		final String fileName =
				Statics.FILE_PATH_SAVE_DATA
						+ filename
						+ Statics.FILE_EXTENSION_LOCATION;
		
		handler.post(new Runnable()
		{
			@SuppressWarnings("unchecked")
			public void run()
			{
				Activity_Main.checkEnvironment();
				if (Activity_Main.extStoAvail)
				{
					FileInputStream fis = null;
					ObjectInputStream ois = null;
					try
					{
						File file = new File(fileName);
						fis = new FileInputStream(file);
						ois = new ObjectInputStream(fis);
						Object objTmp = ois.readObject();
						ActivityLocation.foodItems =
								(ArrayList<ItemFoodCheckContainer>) objTmp;
						
						for (ItemFoodCheckContainer foodItem : ActivityLocation.foodItems)
						{
							foodItem.setChecked(false);
						}
						
						updateList(false);
					} catch (OptionalDataException e)
					{
						Statics.DEBUGred("ActivityLocation - loadBill",
								"OptionalDataException");
						e.printStackTrace();
					} catch (ClassNotFoundException e)
					{
						Statics.DEBUGred("ActivityLocation - loadBill",
								"ClassNotFoundException");
						e.printStackTrace();
					} catch (IOException e)
					{
						Statics.DEBUGred("ActivityLocation - loadBill",
								"IOException");
						e.printStackTrace();
					} catch (Exception e)
					{
						Statics.DEBUGred("ActivityLocation - loadBill",
								"Exception");
						e.printStackTrace();
					} finally
					{
						if (fis != null)
						{
							try
							{
								fis.close();
							} catch (IOException e)
							{}
						}
						if (ois != null)
						{
							try
							{
								ois.close();
							} catch (IOException e)
							{}
						}
					}
				}
			}
		});
	}
	
	private boolean saveLocation()
	{
		View checkBoxView =
				View.inflate(this, R.layout.dialog_name_edittext, null);
		final EditText editTextName =
				(EditText) checkBoxView.findViewById(R.id.editTextName);
		
		editTextName.setText("");
		
		new AlertDialog.Builder(this)
				.setCancelable(true)
				.setView(checkBoxView)
				.setPositiveButton(R.string.text_save,
						new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int id)
							{
								ArrayList<ItemFoodCheckContainer> foodItemsToSave =
										new ArrayList<ItemFoodCheckContainer>();
								for (ItemFoodCheckContainer foodItem : foodItems)
								{
									foodItemsToSave
											.add(new ItemFoodCheckContainer(
													foodItem.getFoodItem()));
								}
								
								String name = editTextName.getText().toString();
								if (name.length() > 0)
									saveLocation(foodItemsToSave, name);
								else
									try
									{
										Toast.makeText(getApplicationContext(),
												R.string.text_invalid_name,
												Toast.LENGTH_LONG).show();
									} catch (Exception e)
									{}
							}
						}).setNegativeButton(R.string.text_abort, null).show();
		return true;
	}
	
	protected static void saveLocation(
			ArrayList<ItemFoodCheckContainer> foodItemsToSave,
			String fileName)
	{
		Statics.DEBUGgreen("ActivityLocation - saveLocation", "save to: "
				+ fileName);
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state))
		{
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try
			{
				File file =
						new File(
								Statics.FILE_PATH_SAVE_DATA
										+ fileName
										+ Statics.FILE_EXTENSION_LOCATION);
				fos = new FileOutputStream(file);
				
				oos = new ObjectOutputStream(fos);
				oos.writeObject(foodItemsToSave);
			} catch (FileNotFoundException fnfe)
			{} catch (IOException ioe)
			{} catch (Exception e)
			{} finally
			{
				try
				{
					oos.flush();
				} catch (Exception e)
				{}
				try
				{
					oos.close();
				} catch (IOException e)
				{}
				try
				{
					fos.close();
				} catch (IOException e)
				{}
			}
		}
	}
	
	private class LocationDropListener implements TouchInterceptor.DropListener
	{
		
		public void drop(int from, int to)
		{
			//Assuming that item is moved up the list
			int direction = -1;
			int loop_start = from;
			int loop_end = to;
			
			//For instance where the item is dragged down the list
			
			if (from < to)
			{
				direction = 1;
			}
			
			ItemFoodCheckContainer target = foodItems.get(from);
			
			for (int i = loop_start; i != loop_end; i = i + direction)
			{
				foodItems.set(i, foodItems.get(i + direction));
			}
			
			foodItems.set(to, target);
			
			((BaseAdapter) listViewFood.getAdapter()).notifyDataSetChanged();
			
		}
		
	};
}
