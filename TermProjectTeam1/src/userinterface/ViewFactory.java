package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {
	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("InventoryManagerView"))
		{
			return new InventoryManagerView(model);
		}
		else if(viewName.equals("AddNewIITActionView"))
		{
			return new AddNewIITActionView(model);
		}
		return null;
	}
}
