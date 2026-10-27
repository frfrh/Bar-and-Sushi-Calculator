package de.freihube.barandsushicalculator;

import java.io.Serializable;


public class ItemFoodCountContainer implements Serializable
{
	private static final long serialVersionUID = 4877300965813073098L;
	
	private ItemFoodList foodItem;
	
	private Integer count;
	
	public ItemFoodCountContainer(String name, ItemFoodList.FoodType type, int priceCent, int iconID)
	{
		foodItem = new ItemFoodList(name, type, priceCent, iconID);
		setCount(0);
	}
	
	public ItemFoodCountContainer(String name, ItemFoodList.FoodType type, int priceUpper,
			int priceLower, int iconID)
	{
		foodItem = new ItemFoodList(name, type, priceUpper,
				priceLower, iconID);
		setCount(0);
	}
	
	public ItemFoodCountContainer(ItemFoodList newFoodItem)
	{
		foodItem = newFoodItem;
		setCount(0);
	}
	
	public ItemFoodCountContainer(ItemFoodList newFoodItem, int newCount)
	{
		foodItem = newFoodItem;
		setCount(newCount);
	}
	
	protected ItemFoodList getFoodItem()
	{
		return foodItem;
	}

	public int getTotalPriceCent()
	{
		return foodItem.getPriceCent() * this.count;
	}
	
	public float getTotalPrice()
	{
		return foodItem.getPrice(foodItem.getPriceCent() * this.count);
	}
	
	
	public String getTotalPriceString()
	{
		int priceLower = ItemFoodList.getPriceLower(foodItem.getPriceCent() * count);
		String output =
				""
						+ ItemFoodList.getPriceUpper(foodItem.getPriceCent() * count)
						+ ItemFoodList.getPriceSeperator()
						+ (priceLower < 10 ? "0" + priceLower : priceLower)
						+ ItemFoodList.getPriceUnit();
		return output;
	}
		
	public int getCount()
	{
		return count;
	}
	
	public void setCount(int count)
	{
		Statics.DEBUGblue("ItemFoodList: " + foodItem.getName(), "Count set to: " + count);
		this.count = count;
	}
	
	public void increaseCount()
	{
		this.count++;
		Statics.DEBUGblue("ItemFoodList: " + foodItem.getName(), "Count increased");
	}
	
	public void decreaseCount()
	{
		this.count--;
		Statics.DEBUGblue("ItemFoodList: " + foodItem.getName(), "Count decreased");
	}
}
