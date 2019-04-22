// specify the package
package model;

// system imports
import javafx.scene.Scene;

import java.sql.Date;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;

/** The class containing the AddVendorInventoryItemTypesAction for the Library application */
//==============================================================
public class AddVIITAction extends Action
{
	private InventoryItemTypeCollection iitList; 
	private VendorCollection vendorList; 
	
	private String inventoryItemTypeName;
	private String vendorId;
	private String vendorPrice;

	// GUI Components
	
	private String actionErrorMessage = "";
//	private String inventoryUpdateStatusMessage = "";
	private String viitUpdateStatusMessage = "";
	
	private InventoryItemType iit;
	private Vendor vendor;
	private VendorInventoryItemType viit;
	
	/**
	 * Constructor for this class.
	 *
	 *
	 */
	//----------------------------------------------------------
	public AddVIITAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("addVIIT", "ActionError");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("VIITData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}


	
	/**
	 * This method encapsulates all the logic of updating the iit
	 */
	//----------------------------------------------------------
	public void showIITList()
	{

		iitList = new InventoryItemTypeCollection();
		iitList.findAllIIT();
		createAndShowIITListView();
//		viitUpdateStatusMessage = (String)iit.getState("UpdateStatusMessage");
			//list = new InventoryItemTypeCollection();
			//list.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			//createAndShowIITListView();
	}

	public void showVendorList()
	{

		vendorList = new VendorCollection();
		vendorList.findAllVendors();
		createAndShowVendorListView();
//		viitUpdateStatusMessage = (String)iit.getState("UpdateStatusMessage");
			//list = new InventoryItemTypeCollection();
			//list.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			//createAndShowIITListView();
	}
	
	
	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
//		if (key.equals("ActionMessage"))
//			return viitUpdateStatusMessage;
		if(key.equals("UpdateStatusMessage"))
			return viitUpdateStatusMessage;
		if(key.equals("InventoryItemTypeList"))
			return iitList;
		if (key.equals("VendorList"))
            return vendorList;
		
		if (iit != null)
			return iit.getState(key);
		return null;
	}

	public void processAction(Properties props)
	{
		vendorId = props.getProperty("VendorId");
		inventoryItemTypeName = props.getProperty("InventoryItemTypeName");
		vendorPrice = props.getProperty("VendorPrice");
		
		if(inventoryItemTypeName != null && vendorId != null && vendorPrice != null)
		{
			viit = new VendorInventoryItemType(props);
			viit.update();
			viitUpdateStatusMessage = (String)viit.getState("UpdateStatusMessage");
		}
	}
	
	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// DEBUG System.out.println("SearchInventoryItemTypesAction.sCR: key: " + key);

		if(key.equals("DoYourJob"))
			showIITList();
//		else if(key.equals("CancelInventoryItemTypeList"))
//			swapToView(createView());
//		else if (key.equals("IITData") == true)
//			processAction();
		else if(key.equals("SelectIIT")) 
		{
			try {
				iit = new InventoryItemType((String)value);
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			showVendorList();
		}
		else if(key.equals("SelectedVendor"))
		{
			try
			{
				vendor = new Vendor((String)value);
			} catch (InvalidPrimaryKeyException e) {
				e.printStackTrace();
			}
			createAndShowPriceView();
		}
		if(key.equals("VIITData"))
		{
			String vendorPrice = (String)value;
			Properties props = new Properties();
			props.setProperty("VendorId", vendor.persistentState.getProperty("vId"));
			props.setProperty("InventoryItemTypeName", iit.persistentState.getProperty("ItemTypeName"));
			props.setProperty("VendorPrice", vendorPrice);
			props.setProperty("DateOfLastUpdate", java.time.LocalDate.now() + "");
			processAction(props);
		}
		
//		else if(key.equals("ModifyIITData"))
//			processAction((Properties)value);
		myRegistry.updateSubscribers(key, this);
	}
	
	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

	
//	public void deleteIIT()
//	{
//		iit.persistentState.setProperty("Status", "Inactive");
//		iit.update();
//	}
	
	/**
	 * Create the view of this class. And then the super-class calls
	 * swapToView() to display the view in the frame
	 */
	//------------------------------------------------------
	protected Scene createView()
	{
		Scene currentScene = myViews.get("InventoryItemTypeCollectionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("InventoryItemTypeCollectionView", this);
			currentScene = new Scene(newView);
			myViews.put("InventoryItemTypeCollectionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
	protected void createAndShowIITListView()
	{
		View newView = ViewFactory.createView("IITCollectionForVIITView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("IITCollectionForAddVIITView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowVendorListView()
	{
		View newView = ViewFactory.createView("VendorCollectionForVIITView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("VendorCollectionForVIITView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowPriceView()
	{
		View newView = ViewFactory.createView("PriceView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("PriceView", currentScene);

		swapToView(currentScene);
	}

}