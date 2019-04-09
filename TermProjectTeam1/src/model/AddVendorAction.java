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
public class AddVendorAction extends Action
{
	// GUI Components

	private String actionErrorMessage = "";
	private String addVendorStatusMessage = "";
	
	private String vName;
	private String vPhone;
	private String vStatus;
	
	private Vendor v;
	

	
	//----------------------------------------------------------
	public AddVendorAction()
		throws Exception
	{
		super();
	}

	//----------------------------------------------------------
	protected void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("AddNewVendor", "ActionError");
		dependencies.setProperty("Cancel", "CancelAction");
		dependencies.setProperty("OK", "CancelAction");
		dependencies.setProperty("VendorData", "UpdateStatusMessage");

		myRegistry.setDependencies(dependencies);
	}


	
	//----------------------------------------------------------
	public void processAction(Properties props)
	{

		vName = props.getProperty("vName");
		vPhone = props.getProperty("vPhone");
		vStatus = props.getProperty("vStatus");

		if(vName != null && vPhone != null && vStatus != null)
		{
			System.out.println(vName + " - " + vPhone + " - " + vStatus);
			v = new Vendor(props);
			v.update();
			addVendorStatusMessage = (String)v.getState("UpdateStatusMessage");
		}
	}


	//-----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError") == true)
		{
			return actionErrorMessage;
		}
		else
		if (key.equals("UpdateStatusMessage") == true)
		{
			return addVendorStatusMessage;
		}
		else if(key.equals("VendorData"))
		{
			String[] vData = {vName, vPhone, vStatus};
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
			processAction((Properties)value);
		

		myRegistry.updateSubscribers(key, this);
	}


	//------------------------------------------------------
	protected Scene createView()
	{

		Scene currentScene = myViews.get("AddVendorActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("AddVendorActionView", this);
			currentScene = new Scene(newView);
			myViews.put("AddVendorActionView", currentScene);

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
		View newView = ViewFactory.createView("AddVendorActionView", this);
		Scene newScene = new Scene(newView);

		myViews.put("AddVendorActionView", newScene);

		// make the view visible by installing it into the stage
		swapToView(newScene);
	}

}