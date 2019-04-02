package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {
	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("InventoryManagerView"))
			return new InventoryManagerView(model);
		else if(viewName.equals("AddVendorActionView"))
			return new AddVendorActionView(model);
		return null;
	}
}
