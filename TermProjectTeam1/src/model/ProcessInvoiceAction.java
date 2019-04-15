// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;


public class ProcessInvoiceAction extends Action{
	
	private String vName, vPhone;
	private String actionErrorMessage = "";
	private String processInvoiceStatusMessage = "";
	
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
		dependencies.setProperty("SearchVesndor", "ActionError");
		dependencies.setProperty("OK", "CancelAction");
		//dependencies.setProperty("ModifyIITData", "ActionMessage");
		dependencies.setProperty("VendorData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * This method encapsulates all the logic of creating the account,
	 * verifying ownership, crediting, etc. etc.
	 */
	//----------------------------------------------------------
	public void processAction(Properties props)
	{

		vName = props.getProperty("vendorName");
		vPhone = props.getProperty("phoneNumber");
		venList = new VendorCollection(vName,vPhone);
		
		createAndShowVendorCollectionView();
	}

	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError"))
			return actionErrorMessage;
		if (key.equals("ActionMessage"))
			return processInvoiceStatusMessage;
//		if(key.equals("UpdateStatusMessage"))
//			return iitUpdateStatusMessage;
		if(key.equals("VendorList"))
			return venList;
		if(key.equals("VendorData"))
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
		else if(key.equals("VendorData"))
			processAction((Properties)value);
		

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
	
}
