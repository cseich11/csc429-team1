package userinterface;

import java.util.Vector;

import javafx.beans.property.SimpleStringProperty;

//==============================================================================
public class VendorTableModel
{
	private final SimpleStringProperty vID;
	private final SimpleStringProperty vName;
	private final SimpleStringProperty vPhone;
	private final SimpleStringProperty vStatus;

	//----------------------------------------------------------------------------
	public VendorTableModel(Vector<String> vendorData)
	{
		vID =  new SimpleStringProperty(vendorData.elementAt(0));
		vName =  new SimpleStringProperty(vendorData.elementAt(1));
		vPhone =  new SimpleStringProperty(vendorData.elementAt(2));
		vStatus =  new SimpleStringProperty(vendorData.elementAt(3));
	}

	//----------------------------------------------------------------------------
	public String getVendorId() {
        return vID.get();
    }

	//----------------------------------------------------------------------------
    public void setBookId(String id) {
        vID.set(id);
    }

    //----------------------------------------------------------------------------
    public String getName() {
        return vName.get();
    }

    //----------------------------------------------------------------------------
    public void setName(String name) {
        vName.set(name);
    }

    //----------------------------------------------------------------------------
    public String getPhoneNum() {
        return vPhone.get();
    }

    //----------------------------------------------------------------------------
    public void setPhoneNum(String num) {
        vPhone.set(num);
    }
    
    //----------------------------------------------------------------------------
    public String getStatus() {
        return vStatus.get();
    }

    //----------------------------------------------------------------------------
    public void setStatus(String status)
    {
    	vStatus.set(status);
    }
}
