package de.freihube.barandsushicalculator;

import java.io.Serializable;

public class ItemFoodCheckContainer implements Serializable
{
	private static final long serialVersionUID = -6067993563171601811L;	
	private ItemFoodList foodItem;
	private boolean checked = false;
	
	public ItemFoodCheckContainer(String name, ItemFoodList.FoodType type, int priceCent, int iconID)
	{
		foodItem = new ItemFoodList(name, type, priceCent, iconID);
		setChecked(false);
	}
	
	public ItemFoodCheckContainer(String name, ItemFoodList.FoodType type, int priceUpper,
			int priceLower, int iconID)
	{
		foodItem = new ItemFoodList(name, type, priceUpper,
				priceLower, iconID);
		setChecked(false);
	}
	
	public ItemFoodCheckContainer(ItemFoodList itemFoodList)
	{
		foodItem = itemFoodList;
		setChecked(false);
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

	public ItemFoodList getFoodItem()
	{
		return foodItem;
	}
}
