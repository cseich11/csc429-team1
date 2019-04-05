package userinterface;

import impresario.IModel;

//==============================================================================
public class ViewFactory {
	public static View createView(String viewName, IModel model)
	{
		if(viewName.equals("InventoryManagerView"))
			return new InventoryManagerView(model);
		if(viewName.equals("AddNewIITActionView"))
			return new AddNewIITActionView(model);
		if(viewName.equals("SearchIITActionView"))
			return new SearchIITActionView(model);
		if(viewName.equals("InventoryItemTypeCollectionView"))
			return new InventoryItemTypeCollectionView(model);
		if(viewName.equals("ModifyIITActionView"))
			return new ModifyIITActionView(model);
		return null;
	}
}
