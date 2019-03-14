// specify the package
package model;

// system imports
import java.util.Vector;
import javax.swing.JFrame;

// project imports

/** The class containing the ActionFactory for the Restaurant Inventory application */
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

		if (actionType.equals("addVendor") == true)
		{
			retValue = new addVendor();
		}
		
		return retValue;
	}
}
