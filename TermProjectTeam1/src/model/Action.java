// specify the package
package model;

// system imports
import java.util.Hashtable;
import java.util.Properties;
import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import event.Event;

import impresario.*;

import userinterface.MainStageContainer;
import userinterface.WindowPosition;

/** The class containing the Action for the ATM application */
//==============================================================
abstract public class Action implements IView, IModel
{

	// For Impresario
	protected Properties dependencies;
	protected ModelRegistry myRegistry;

	protected Stage myStage;
	protected Hashtable<String, Scene> myViews;
	// GUI Components

	/**
	 * Constructor for this class.
	 *
	 * Action remembers all the account IDs for this customer.
	 * It uses AccountCatalog to create this list of account IDs.
	 *
	 */
	//----------------------------------------------------------
	protected Action() throws Exception
	{

		myStage = MainStageContainer.getInstance();
		myViews = new Hashtable<String, Scene>();

		myRegistry = new ModelRegistry("Action");
		if(myRegistry == null)
		{
			new Event(Event.getLeafLevelClassName(this), "Action",
				"Could not instantiate Registry", Event.ERROR);
		}
		setDependencies();

	}

	//----------------------------------------------------------
	protected abstract void setDependencies();

	//---------------------------------------------------------
	protected abstract Scene createView();

	/**
	 * Template method
	 *
	 */
	//---------------------------------------------------------
	protected void doYourJob()
	{
		Scene newScene = createView();			
		swapToView(newScene);
	}

	// forward declarations
	//-----------------------------------------------------------
	public abstract Object getState(String key);

	//-----------------------------------------------------------
	public abstract void stateChangeRequest(String key, Object value);

	/** Called via the IView relationship
	 * Re-define in sub-class, if necessary
	 */
	//----------------------------------------------------------
	public void updateState(String key, Object value)
	{
		stateChangeRequest(key, value);
	}

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
			System.out.println("Action.swapToView(): Missing view for display");
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

