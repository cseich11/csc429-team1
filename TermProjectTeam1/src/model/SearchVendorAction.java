// specify the package
package model;

// system imports
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.util.Properties;
import java.util.Vector;

// project imports
import event.Event;
import exception.InvalidPrimaryKeyException;

import userinterface.View;
import userinterface.ViewFactory;

//==============================================================
public class SearchVendorAction extends Action
{
	// GUI Components

	private String actionErrorMessage = "";
	
	private String vName;
	private String vPhone;
//	private String vStatus;
	
//	private Vendor v;
	
	private VendorCollection vc;
	

	
	//----------------------------------------------------------
	public SearchVendorAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("SearchVendor", "ActionError");
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("VendorData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}


	
	//----------------------------------------------------------
	public void processAction(Properties props)
	{

		vName = props.getProperty("vendorName");
		vPhone = props.getProperty("phoneNumber");
		System.out.println(vName + " " + vPhone);
		vc = new VendorCollection(vName,vPhone);
		
		vc.createAndShowVendorCollectionView();
	}


	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError") == true)
		{
			return actionErrorMessage;
		}
//		else
//		if (key.equals("UpdateStatusMessage") == true)
//		{
//			return addVendorStatusMessage;
//		}
		else if(key.equals("SearchVendor"))
		{
			String[] vData = {vName, vPhone};
			return vData;
		}
		
		return null;
	}

	//-----------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		if (key.equals("DoYourJob") == true)
			doYourJob();
		else if (key.equals("VendorData") == true)
		{
			System.out.println("test");
			processAction((Properties)value);
		}
		

		myRegistry.updateSubscribers(key, this);
	}


	//------------------------------------------------------
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


	//------------------------------------------------------
	protected void createAndShowView()
	{
		Scene newScene;
		
		if(vc != null)
		{
			View newView = ViewFactory.createView("SearchVendorActionView", this);
			newScene = new Scene(newView);
	
			myViews.put("SearchVendorActionView", newScene);
		}
		else
		{
			View newView = ViewFactory.createView("VendorCollectionView", this);
			newScene = new Scene(newView);
	
			myViews.put("VendorCollectionView", newScene);
			
		}

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}

}