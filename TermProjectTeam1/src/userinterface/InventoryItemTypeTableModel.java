package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class InventoryItemTypeTableModel
{
	private final SimpleStringProperty itemTypeName;
	private final SimpleStringProperty units;
	private final SimpleStringProperty unitMeasure;
	private final SimpleStringProperty validityDays;
	private final SimpleStringProperty reorderPoint;
	private final SimpleStringProperty notes;
	private final SimpleStringProperty status;

	//----------------------------------------------------------------------------
	public InventoryItemTypeTableModel(Vector<String> iitData)
	{
		itemTypeName =  new SimpleStringProperty(iitData.elementAt(0));
		units =  new SimpleStringProperty(iitData.elementAt(1));
		unitMeasure =  new SimpleStringProperty(iitData.elementAt(2));
		validityDays =  new SimpleStringProperty(iitData.elementAt(3));
		reorderPoint =  new SimpleStringProperty(iitData.elementAt(4));
		notes =  new SimpleStringProperty(iitData.elementAt(5));
		status =  new SimpleStringProperty(iitData.elementAt(6));
	}

	//----------------------------------------------------------------------------
	public String getItemTypeName() 
	{
        return itemTypeName.get();
    }

	//----------------------------------------------------------------------------
    public void setItemTypeName(String name)
    {
        itemTypeName.set(name);
    }

    //----------------------------------------------------------------------------
    public String getUnits()
    {
        return units.get();
    }

    //----------------------------------------------------------------------------
    public void setUnits(String u)
    {
        units.set(u);
    }

    //----------------------------------------------------------------------------
    public String getUnitMeasure() 
    {
        return unitMeasure.get();
    }

    //----------------------------------------------------------------------------
    public void setUnitMeasure(String unitMeas)
    {
        unitMeasure.set(unitMeas);
    }
    
    //----------------------------------------------------------------------------
    public String getValidityDays() 
    {
        return validityDays.get();
    }

    //----------------------------------------------------------------------------
    public void setValidityDays(String vDays)
    {
    	validityDays.set(vDays);
    }
    
  //----------------------------------------------------------------------------
    public String getReorderPoint()
    {
        return reorderPoint.get();
    }

    //----------------------------------------------------------------------------
    public void setReorderPoint(String reorder)
    {
    	reorderPoint.set(reorder);
    }
    
    //----------------------------------------------------------------------------
    public String getNotes()
    {
        return notes.get();
    }

    //----------------------------------------------------------------------------
    public void setNotes(String note)
    {
    	notes.set(note);
    }
    
    //----------------------------------------------------------------------------
    
    public String getStatus()
    {
        return status.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String s)
    {
    	status.set(s);
    }
}
