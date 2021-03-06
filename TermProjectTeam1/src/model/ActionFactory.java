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
		if(actionType.equals("AddVendor"))
			return new AddVendorAction();
		if(actionType.equals("SearchVendor"))
			return new SearchVendorAction();
		if(actionType.equals("ModifyVendor"))
			return new ModifyVendorAction();
		if (actionType.equals("AddNewIIT"))
			return new AddNewIITAction();
		if(actionType.equals("SearchIIT"))
			return new SearchIITAction();
		if(actionType.equals("SearchII"))
			return new SearchIIAction();
		if(actionType.equals("ModifyIIT"))
			return new ModifyIITAction();
		if(actionType.equals("ProcessInvoice"))
			return new ProcessInvoiceAction();
		if(actionType.equals("AddVIIT"))
			return new AddVIITAction();
		if(actionType.equals("DeleteVIIT"))
			return new DeleteVIITAction();
		if(actionType.equals("GenerateReorderList"))
			return new ReorderListAction();
		if(actionType.equals("GetIICollection"))
			return new GetIICollectionAction();
		
		return null;
	}
}
