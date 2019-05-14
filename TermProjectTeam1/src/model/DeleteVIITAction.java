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
public class DeleteVIITAction extends Action
{
	private InventoryItemTypeCollection iitList; 
	private VendorCollection vendorList; 
	
	private String inventoryItemTypeName;
	private String vendorId;
	private String vendorPrice;
	private String[] data;

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
	public DeleteVIITAction()
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
	public void showIITList(String data[])
	{
		if(data.length == 2 && data[0] != null && data[1] != null)
		{
			iitList = new InventoryItemTypeCollection();
			String itemTypeNameSearched = data[0]; String notesSearched = data[1];
			iitList.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			createAndShowIITs();
		}
//		iitList = new InventoryItemTypeCollection();
//		iitList.findAllIIT();
//		createAndShowIITListView();
//		viitUpdateStatusMessage = (String)iit.getState("UpdateStatusMessage");
			//list = new InventoryItemTypeCollection();
			//list.findAllIITWithNameNotes(itemTypeNameSearched, notesSearched);
			//createAndShowIITListView();
	}

	public void showVendorList(Properties props)
	{
		String vName = props.getProperty("vendorName");
		String vPhone = props.getProperty("phoneNumber");
		System.out.println(vName + " - " + vPhone);
		vendorList = new VendorCollection();
		vendorList.findVendors(vName, vPhone);
		createAndShowVendors();
		
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
		if (key.equals("VIIT"))
            return viit;
		
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
			createAndShowVendorSearch();
//		else if(key.equals("CancelInventoryItemTypeList"))
//			swapToView(createView());
//		else if (key.equals("IITData") == true)
//			processAction();
		else if(key.equals("SelectIIT")) 
		{
			try {
				iit = new InventoryItemType((String)value);
				viit = new VendorInventoryItemType(vendor.persistentState.getProperty("vId"), iit.persistentState.getProperty("ItemTypeName"));
				createAndShowConfirmDeleteView();
			} catch (InvalidPrimaryKeyException e) {
				// TODO Auto-generated catch block
				viitUpdateStatusMessage = "This is not a valid VIIT"; 
				createAndShowVendorSearch();
			}
			
		}
		else if(key.equals("Delete"))
		{
			viit.delete();
			viitUpdateStatusMessage = (String)viit.getState("UpdateStatusMessage");
		}
		else if(key.equals("SelectedVendor"))
		{
			try
			{
				vendor = new Vendor((String)value);
			} catch (InvalidPrimaryKeyException e) {
				e.printStackTrace();
			}
			createAndShowIITSearch();
		}
		else if (key.equals("VendorData") == true)
		{
			data = (String[]) value;
			System.out.println(data[0] + " - " + data[1]);
			Properties props = new Properties();
			props.setProperty("vendorName", data[0]);
			props.setProperty("phoneNumber", data[1]);
			showVendorList((Properties)props);
		}
		else if(key.equals("IITData"))
			showIITList((String[])value);
		else if(key.equals("CancelItemTypeSearch"))
			createAndShowVendors();
		else if(key.equals("CancelVendorList"))
			createAndShowVendorSearch();
		else if(key.equals("CancelIITList"))
			createAndShowIITSearch();
		
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
	
	protected void createAndShowVendorSearch()
	{
		View newView = ViewFactory.createView("SearchVendorActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("SearchVendorActionView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowVendors()
	{
		View newView = ViewFactory.createView("VendorCollectionForVIITView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("VendorCollectionForVIITView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowIITs()
	{
		View newView = ViewFactory.createView("IITCollectionForVIITView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("IITCollectionForVIITView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowIITSearch()
	{
		View newView = ViewFactory.createView("SearchIITActionView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("SearchIITActionView", currentScene);

		swapToView(currentScene);
	}
	
	protected void createAndShowConfirmDeleteView()
	{
		View newView = ViewFactory.createView("ConfirmDeleteVIITView", this);
		Scene currentScene = new Scene(newView);
		myViews.put("ConfirmDeleteVIITView", currentScene);

		swapToView(currentScene);
	}

}