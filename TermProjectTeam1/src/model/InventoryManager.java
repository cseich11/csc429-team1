// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;

import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.IView;
import impresario.ModelRegistry;

import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/** The class containing the Inventory Manager for the Restaurant Inventory application */
//==============================================================
public class InventoryManager implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
	// For Impresario
	private Properties dependencies;
	private ModelRegistry myRegistry;

	// GUI Components
	private Hashtable<String, Scene> myViews;
	private Stage myStage;

	private String actionErrorMessage = "";

	// constructor for this class
	//----------------------------------------------------------
	public InventoryManager()
	{
		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		// STEP 3.1: Create the Registry object - if you inherit from
		// EntityBase, this is done for you. Otherwise, you do it yourself
		myRegistry = new ModelRegistry("InventoryManager");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "InventoryManager",
				"Could not instantiate Registry", Event.ERROR);
		}

		// STEP 3.2: Be sure to set the dependencies correctly
		setDependencies();

		// Set up the initial view
		createAndShowInventoryManagerView();
	}

	//-----------------------------------------------------------------------------------
	private void setDependencies()
	{
		dependencies = new Properties();
		dependencies.setProperty("AddIIT", "ActionError");

		myRegistry.setDependencies(dependencies);
	}

	/**
	 * Method called from client to get the value of a particular field
	 * held by the objects encapsulated by this object.
	 *
	 * @param	key	Name of database column (field) for which the client wants the value
	 *
	 * @return	Value associated with the field
	 */
	//----------------------------------------------------------
	public Object getState(String key)
	{
		if (key.equals("ActionError") == true)
			return actionErrorMessage;
		else
			return "";
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
		// STEP 4: Write the sCR method component for the key you
		// just set up dependencies for
		// DEBUG System.out.println("InventoryManager.sCR: key = " + key);
		if (key.equals("AddIIT"))
		{
			doAction(key);
		}
		if (key.equals("AddVendor"))
		{
			doAction(key);
		}
		else if (key.equals("CancelAction"))
		{
			createAndShowInventoryManagerView();
		}

		myRegistry.updateSubscribers(key, this);
	}

	/** Called via the IView relationship */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		// DEBUG System.out.println("InventoryManager.updateState: key: " + key);

		stateChangeRequest(key, value);
	}

	/**
	 * Create a Action depending on the Action type (add iit, etc.).
	 */
	//----------------------------------------------------------
	public void doAction(String actionType)
	{
		try
		{
			Action action = ActionFactory.createAction(actionType);
			
			action.subscribe("CancelAction", this);
			action.stateChangeRequest("DoYourJob", "");
		}
		catch (Exception ex)
		{
			actionErrorMessage = "FATAL ERROR: ACTION FAILURE: Unrecognized action!!";
			new Event(Event.getLeafLevelClassName(this), "createAction",
					"Action Creation Failure: Unrecognized action " + ex.toString(),
					Event.ERROR);
		}
	}

	//----------------------------------------------------------
	private void createAndShowInventoryManagerView()
	{
		Scene currentScene = (Scene)myViews.get("InventoryManagerView");
		
		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("InventoryManagerView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("InventoryManagerView", currentScene);
		}
				

		// make the view visible by installing it into the frame
		//swapToView(currentScene);


		myStage.setScene(currentScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);
	}

	//------------------------------------------------------------
	/**private void createAndShowInventoryManagerView()
	{
		Scene currentScene = (Scene)myViews.get("InventoryManagerView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("InventoryManagerView", this); // USE VIEW FACTORY
			currentScene = new Scene(newView);
			myViews.put("InventoryManagerView", currentScene);
		}
				
		swapToView(currentScene);
		
	}*/


	/** Register objects to receive state updates. */
	//----------------------------------------------------------
	public void subscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
		// forward to our registry
		myRegistry.subscribe(key, subscriber);
	}

	/** Unregister previously registered objects. */
	//----------------------------------------------------------
	public void unSubscribe(String key, IView subscriber)
	{
		// DEBUG: System.out.println("Cager.unSubscribe");
		// forward to our registry
		myRegistry.unSubscribe(key, subscriber);
	}



	//-----------------------------------------------------------------------------
	public void swapToView(Scene newScene)
	{
		if (newScene == null)
		{
			System.out.println("InventoryManager.swapToView(): Missing view for display");
			new Event(Event.getLeafLevelClassName(this), "swapToView",
				"Missing view for display ", Event.ERROR);
			return;
		}

		myStage.setScene(newScene);
		myStage.sizeToScene();
		
			
		//Place in center
		WindowPosition.placeCenter(myStage);

	}

}

