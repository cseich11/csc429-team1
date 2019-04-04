// specify the package
package model;

// system imports
import java.util.Vector;
import javax.swing.JFrame;

// project imports

//==============================================================
public class ActionFactory
{

	/**
	 *
	 */
	//----------------------------------------------------------
	public static Action createAction(String actionType)
		throws Exception
	{
		Action retValue = null;
		
		if(actionType.equals("AddVendor") == true)
		{
			retValue = new AddVendorAction();
		}
		else if(actionType.equals("SearchVendor") == true)
		{
			retValue = new SearchVendorAction();
		}
		return retValue;
	}
}
