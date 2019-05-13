
// specify the package
package userinterface;

// system imports
import java.text.NumberFormat;
import java.util.Properties;

import javafx.event.Event;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// project imports
import impresario.IModel;

/** The class containing the Inventory Manager View  for the Library application */
//==============================================================
public class InventoryManagerView extends View 
{

	// GUI stuff
	private Button insertIITButton, searchIITButton, searchIIButton, doneButton, addVendorButton, searchVendorButton, addVIITButton, deleteVIITButton, generateReorderListButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public InventoryManagerView( IModel InventoryManager)
	{

		super(InventoryManager, "InventoryManagerView");

		// create a container for showing the contents
		VBox container = new VBox(10);

		container.setPadding(new Insets(15, 5, 5, 5));

		// create a Node (Text) for showing the title
		container.getChildren().add(createTitle());

		// create a Node (GridPane) for showing data entry fields
		container.getChildren().add(createFormContents());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);
		
		myModel.subscribe("ActionError", this);
	}

	// Create the label (Text) for the title of the screen
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);
		
		Text titleText = new Text("       Restaurant Inventory System          ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		titleText.setStrokeWidth(0.5);
		titleText.setStroke(Color.GOLDENROD);  
        titleText.setTextAlignment(TextAlignment.CENTER);	
        
        container.getChildren().add(titleText);

        return container;
	}

	// Create the main form contents
	//-------------------------------------------------------------
	private GridPane createFormContents()
	{
		GridPane grid = new GridPane();
        	grid.setAlignment(Pos.CENTER);
       		grid.setHgap(10);
        	grid.setVgap(10);
        	grid.setPadding(new Insets(25, 25, 25, 25));

		// action choice buttons
        insertIITButton = new Button("Add New Inventory Item Type");
        insertIITButton.setOnAction(e -> {
 			myModel.stateChangeRequest("AddNewIIT", null);
        });
        
        searchIITButton = new Button("Search Inventory Item Type");
        searchIITButton.setOnAction(e -> {
 			myModel.stateChangeRequest("SearchIIT", null);
        });
        
        searchIIButton = new Button("Search Inventory Item");
        searchIIButton.setOnAction(e -> {
 			myModel.stateChangeRequest("SearchII", null);
        });
        
        addVendorButton = new Button("Add New Vendor");
        addVendorButton.setOnAction(e -> {
        	myModel.stateChangeRequest("AddVendor", null);
        });
        
        searchVendorButton = new Button("Search Vendor");
        searchVendorButton.setOnAction(e -> {
        	myModel.stateChangeRequest("SearchVendor", null);
        });
        
        addVIITButton = new Button("Add Vendor Inventory Item Type");
        addVIITButton.setOnAction(e -> {
        	myModel.stateChangeRequest("AddVIIT", null);
        });
        
        deleteVIITButton = new Button("Delete Vendor Inventory Item Type");
        deleteVIITButton.setOnAction(e -> {
        	myModel.stateChangeRequest("DeleteVIIT", null);
        });
        
        generateReorderListButton = new Button("Generate Reorder List");
        generateReorderListButton.setOnAction(e -> {
        	myModel.stateChangeRequest("GenerateReorderList", null);
        });
        	
		doneButton = new Button("Done");
 		doneButton.setOnAction(e -> {
 			done(e);
        });
 		grid.add(addVendorButton, 0, 0);
 		grid.add(searchVendorButton, 1, 0);
 		grid.add(insertIITButton, 0, 1); 
 		grid.add(searchIITButton, 1, 1);
 		grid.add(deleteVIITButton, 0, 2);
 		grid.add(addVIITButton, 1, 2);
 		grid.add(searchIIButton, 0, 3);
 		grid.add(generateReorderListButton, 1, 3);
		grid.add(doneButton, 0, 4);

		return grid;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{

		statusLog = new MessageView(initialMessage);

		return statusLog;
	}
	
	public void done(Event evt)
	{
		// DEBUG: System.out.println("InventoryManagerView.actionPerformed()");

		clearErrorMessage();

		System.exit(0);

	}

	//---------------------------------------------------------
	/**
	 * Required by interface, but has no functionality here
	 */
	public void updateState(String key, Object value)
	{
		
	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

}