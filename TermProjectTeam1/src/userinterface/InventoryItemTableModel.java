package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class InventoryItemTableModel
{
    private final SimpleStringProperty barcode;
    private final SimpleStringProperty inventoryItemTypeName;
    private final SimpleStringProperty vendor;
    private final SimpleStringProperty dateRecieved;
    private final SimpleStringProperty dateLastUsed;
    private final SimpleStringProperty notes;
    private final SimpleStringProperty status;

    //----------------------------------------------------------------------------
    public InventoryItemTableModel(Vector<String> iitData)
    {
        barcode =  new SimpleStringProperty(iitData.elementAt(0));
        inventoryItemTypeName =  new SimpleStringProperty(iitData.elementAt(1));
        vendor =  new SimpleStringProperty(iitData.elementAt(2));
        dateRecieved =  new SimpleStringProperty(iitData.elementAt(3));
        dateLastUsed =  new SimpleStringProperty(iitData.elementAt(4));
        notes =  new SimpleStringProperty(iitData.elementAt(5));
        status =  new SimpleStringProperty(iitData.elementAt(6));
    }

    //----------------------------------------------------------------------------
    public String getBarcode()
    {
        return barcode.get();
    }

    //----------------------------------------------------------------------------
    public void setBarcode(String name)
    {
        barcode.set(name);
    }

    //----------------------------------------------------------------------------
    public String getInventoryItemTypeName()
    {
        return inventoryItemTypeName.get();
    }

    //----------------------------------------------------------------------------
    public void setInventoryItemTypeName(String u)
    {
        inventoryItemTypeName.set(u);
    }

    //----------------------------------------------------------------------------
    public String getVendor()
    {
        return vendor.get();
    }

    //----------------------------------------------------------------------------
    public void setVendor(String unitMeas)
    {
        vendor.set(unitMeas);
    }

    //----------------------------------------------------------------------------
    public String getDateRecieved()
    {
        return dateRecieved.get();
    }

    //----------------------------------------------------------------------------
    public void setDateRecieved(String vDays)
    {
        dateRecieved.set(vDays);
    }

    //----------------------------------------------------------------------------
    public String getDateLastUsed()
    {
        return dateLastUsed.get();
    }

    //----------------------------------------------------------------------------
    public void getDateLastUsed(String reorder)
    {
        dateLastUsed.set(reorder);
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
