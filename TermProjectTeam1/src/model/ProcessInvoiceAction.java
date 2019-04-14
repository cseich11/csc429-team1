// specify the package
package model;

// system imports
import javafx.scene.Scene;
import java.util.Properties;

import exception.InvalidPrimaryKeyException;
import userinterface.View;
import userinterface.ViewFactory;


public class ProcessInvoiceAction extends Action{
	
	private String actionErrorMessage = "";
	private String processInvoiceStatusMessage = "";
	

	protected ProcessInvoiceAction() 
			throws Exception 
	{
		super();
		
	}

	protected Scene createView()
	{
		Scene currentScene = myViews.get("ProcessInvoiceActionView");

		if (currentScene == null)
		{
			// create our initial view
			View newView = ViewFactory.createView("ProcessInvoiceActionView", this);
			currentScene = new Scene(newView);
			myViews.put("ProcessInvoiceActionView", currentScene);

			return currentScene;
		}
		else
		{
			return currentScene;
		}
	}
	
}
