package de.freihube.barandsushicalculator;

import java.util.ArrayList;

public class DeleteContainer
{
	private ArrayList<ItemFoodCountContainer> list;
	private int numberToDelete;
	
	public DeleteContainer(ArrayList<ItemFoodCountContainer> list, int numberToDelete)
	{
		this.list = list;
		this.numberToDelete=numberToDelete;
	}
	
	public void delete()
	{
		this.list.remove(numberToDelete);
	}
}
