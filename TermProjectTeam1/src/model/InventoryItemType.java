package model;

public class InventoryItemType 
{
	private String itemTypeName;
	private int units;
	private String unitMeasure;
	private int validityDays;
	private double reorderPoint;
	private String notes;
	private String status;
	
	public InventoryItemType(String itemTypeName, int units, String unitMeasure, int validityDays, double reorderPoint, String notes, String status)
	{
		this.itemTypeName = itemTypeName;
		this.units = units;
		this.unitMeasure = unitMeasure;
		this.validityDays = validityDays;
		this.reorderPoint = reorderPoint;
		this.notes = notes;
		this.status = status;
	}
	
	public String getItemTypeName()
	{
		return itemTypeName;
	}
	
	public void setItemTypeName(String itemTypeName)
	{
		this.itemTypeName = itemTypeName;
	}
	
	public int getUnits()
	{
		return units;
	}
	
	public void setUnits(int units)
	{
		this.units = units;
	}
	
	public String getUnitMeasure()
	{
		return unitMeasure;
	}
	
	public void setUnitMeasure(String unitMeasure)
	{
		this.unitMeasure = unitMeasure;
	}
	
	public int getValidityDays()
	{
		return validityDays;
	}
	
	public void setValidityDays(int validityDays)
	{
		this.validityDays = validityDays;
	}
	
	public double getReorderPoint()
	{
		return reorderPoint;
	}
	
	public void setReorderPoint(double reorderPoint)
	{
		this.reorderPoint = reorderPoint;
	}
	
	public String getNotes()
	{
		return notes;
	}
	
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
}
