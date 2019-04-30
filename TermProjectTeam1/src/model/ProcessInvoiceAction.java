// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;


public class ProcessInvoiceAction extends Action{
	
	private String vId, vName, vPhone, vStatus, itemTypeNameSearched, notesSearched, notesEntered, barcodeEntered, iitNameEntered;
	private String actionErrorMessage = "";
	private String processInvoiceStatusMessage = "";
	private String addVendorStatusMessage = "";
	
	private InventoryItemType iit;
	private InventoryItemTypeCollection iitList;
	private Vendor ven;
	private VendorCollection venList;
	

	//-----------------------------------------------------------
	protected ProcessInvoiceAction() 
			throws Exception 
	{
		super();
		
	}
	
	//-----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("SearchVendor", "ActionError");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("VendorData", "UpdateStatusMessage");
		dependencies.setProperty("SearchIIT", "ActionError");
		dependencies.setProperty("ModifyIITData", "ActionMessage");
		dependencies.setProperty("IITData", "UpdateStatusMessage");
		dependencies.setProperty("ModifyVendor", "ActionError");


		myRegistry.setDependencies(dependencies);
	}

	
	//----------------------------------------------------------
	public void processAction(String[] data)
	{
		if(data.length == 2 && data[0] != null && data[1] != null)
		{
			iitList = new InventoryItemTypeCollection();
			itemTypeNameSearched = data[0]; notesSearched = data[1];
			iitList.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			
			
			
			createAndShowIITListView();
		}
	}
	
	/**
	 * This method encapsulates all the logic of creating the account,
	 * verifying ownership, crediting, etc. etc.
	 */
	//----------------------------------------------------------
	public void processActionSearch(String[] data)
	{

		vName = data[0];
		vPhone = data[1];
		venList = new VendorCollection();
		venList.findVendors(vName, vPhone);
		
		createAndShowVendorCollectionView();
	}
	
	
	//-----------------------------------------------------------
	public void processActionModify(Properties props)
	{
		props.setProperty("vId", vId);
		vName = props.getProperty("vendorName");
		vPhone = props.getProperty("phoneNumber");
		vStatus = props.getProperty("status");
		
		
		System.out.println(vName + " - " + vPhone + " - " + vStatus);

		if(vName != null && vPhone != null && vStatus != null)
		{
			ven = new Vendor(props);
			ven.update();
			addVendorStatusMessage = (String)ven.getState("UpdateStatusMessage");
		}
	}
	
	
	
	//-----------------------------------------------------------
	public void processInvoiceAction(Properties props)
	{

		iitNameEntered = props.getProperty("i");
		barcodeEntered = props.getProperty("b");
		notesEntered = props.getProperty("n");
		
		
		createAndShowVendorCollectionView();
	}
	

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
		if (key.equals("ActionMessage"))
			return processInvoiceStatusMessage;
		if(key.equals("InventoryItemTypeList"))
			return iitList;
		if(key.equals("showSubmitButton"))
			return true;
		
//		if(key.equals("UpdateStatusMessage"))
//			return iitUpdateStatusMessage;
		
		if(key.equals("VendorList"))
			return venList;
		if(key.equals("VendorData") || key.equals("SearchVendor"))
		{
			String[] vendorData = {vName, vPhone};
			return vendorData;
		}
		if (iit != null)
			return iit.getState(key);
		
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("SearchInventoryItemTypesAction.sCR: key: " + key);

		if(key.equals("DoYourJob"))
			doYourJob();
		
		else if(key.equals("CancelVendorList"))
			swapToView(createView());
		
		else if(key.equals("VendorData"))
			processActionSearch((String[]) value);
		
		else if(key.equals("ProcessInvoice")) 
		{
			vId = (String) value;
			createAndShowSubmitInvoiceView();
		}
		
		else if(key.equals("InvoiceData")) 
		{
			processInvoiceAction((Properties)value);
		}
		
		else if(key.equals("ModifyVendor")) {
			try {
				vId = (String) value;
				ven = new Vendor(vId);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createAndShowModifyVendorView();
		}
		
		else if(key.equals("ModifyVendorData"))
			processActionModify((Properties) value);
		
		else if(key.equals("AddVIIT"))
		{
			vId = (String) value;
		
			try {
				ven = new Vendor(vId);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			createAndShowSearchIITActionView();
		}
		
		else if(key.equals("IITData"))
			processAction((String[]) value);
			
		
			
		

		myRegistry.updateSubscribers(key, this);
	}
	
	//-----------------------------------------------------------
	protected Scene createView()
	{
		Scene currentScene = myViews.get("SearchVendorActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("SearchVendorActionView", this);
			currentScene = new Scene(newView);
			myViews.put("SearchVendorActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
	//-----------------------------------------------------------
	protected void createAndShowVendorCollectionView()
    {

        Scene localScene = myViews.get("VendorCollectionView");

        if (localScene == null)
        {
            // create our new view
            View newView = ViewFactory.createView("VendorCollectionView", this);
            localScene = new Scene(newView);
            myViews.put("VendorCollectionView", localScene);
        }
        // make the view visible by installing it into the frame
        swapToView(localScene);
		
    }
	
	//-----------------------------------------------------------
	protected void createAndShowSearchIITActionView()
	{
		View newView = ViewFactory.createView("SearchIITActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("SearchIITActionView", currentScene);

		swapToView(currentScene);
	}
	
	//-----------------------------------------------------------
	protected void createAndShowIITListView()
	{
		View newView = ViewFactory.createView("InventoryItemTypeCollectionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("InventoryItemTypeCollectionView", currentScene);

		swapToView(currentScene);
	}
	
	//-----------------------------------------------------------
	protected void createAndShowModifyVendorView()
	{
		View newView = ViewFactory.createView("ModifyVendorView", this);
		Scene newScene = new Scene(newView);

		myViews.put("ModifyVendorView", newScene);

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}
	
	protected void createAndShowSubmitInvoiceView()
	{
		View newView = ViewFactory.createView("SubmitInvoiceView", this);
		Scene newScene = new Scene(newView);

		myViews.put("SubmitInvoiceView", newScene);

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}
	
	
	
}
