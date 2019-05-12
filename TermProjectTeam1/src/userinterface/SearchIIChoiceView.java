//specify the package
package userinterface;

import javafx.event.ActionEvent;
//system imports
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

//project imports
import impresario.IModel;

/** The class containing the Deposit Amount View  for the Library application */
//==============================================================
public class SearchIIChoiceView extends View
{

	// Model

	// GUI components
	
	protected TextField Barcode;
	protected TextField InventoryItemTypeName;
	protected TextField Vendor;
	protected TextField DateRecieved;
	protected TextField DateLastUsed;
	protected TextField Notes;
	
	protected TextField Status;

	private Button modifyButton;
	private Button deleteButton;
	private Button cancelButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchIIChoiceView(IModel action)
	{
		super(action, "SearchIIChoiceView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                          "));

		getChildren().add(container);

		populateFields();
		
		myModel.subscribe("UpdateStatusMessage", this);
		myModel.subscribe("ActionError", this);
		
	}


	// Create the label (Text) for the title
	//-------------------------------------------------------------
	private Node createTitle()
	{
		
		Text titleText = new Text("       Choose An Action          ");
		titleText.setWrappingWidth(300);
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		
		return titleText;
	}

	// Create the main form content
	//-------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
       	grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text prompt = new Text("INVENTORY ITEM INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text barcodeLabel = new Text(" Barcode : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		barcodeLabel.setFont(myFont);
		barcodeLabel.setWrappingWidth(150);
		barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(barcodeLabel, 0, 1);

		Barcode = new TextField();
		Barcode.setEditable(false);
		grid.add(Barcode, 1, 1);
		

		Text iitNameLabel = new Text(" Inventory Item Type Name : ");
		iitNameLabel.setFont(myFont);
		iitNameLabel.setWrappingWidth(150);
		iitNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(iitNameLabel, 0, 2);

		InventoryItemTypeName = new TextField();
		InventoryItemTypeName.setEditable(false);
		grid.add(InventoryItemTypeName, 1, 2);
		

		Text vendorLabel = new Text(" Vendor : ");
		vendorLabel.setFont(myFont);
		vendorLabel.setWrappingWidth(150);
		vendorLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vendorLabel, 0, 3);

		Vendor = new TextField();
		Vendor.setEditable(false);
		grid.add(Vendor, 1, 3);
		
		
		Text dateRecievedLabel = new Text(" Date Recieved : ");
		dateRecievedLabel.setFont(myFont);
		dateRecievedLabel.setWrappingWidth(150);
		dateRecievedLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dateRecievedLabel, 0, 4);

		DateRecieved = new TextField();
		DateRecieved.setEditable(false);
		grid.add(DateRecieved, 1, 4);
		
		
		Text dateLastUsedLabel = new Text(" Date Last Used : ");
		dateLastUsedLabel.setFont(myFont);
		dateLastUsedLabel.setWrappingWidth(150);
		dateLastUsedLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(dateLastUsedLabel, 0, 5);

		DateLastUsed = new TextField();
		DateLastUsed.setEditable(false);
		grid.add(DateLastUsed, 1, 5);
	
		
		Text notesLabel = new Text(" Notes : ");
		notesLabel.setFont(myFont);
		notesLabel.setWrappingWidth(150);
		notesLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(notesLabel, 0, 6);

		Notes = new TextField();
		Notes.setEditable(false);
		grid.add(Notes, 1, 6);
		
		
		Text statusLabel = new Text(" Status : ");
		statusLabel.setFont(myFont);
		statusLabel.setWrappingWidth(150);
		statusLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(statusLabel, 0, 7);
		
		Status = new TextField();
		Status.setEditable(false);
		grid.add(Status, 1, 7);
		

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		
		modifyButton = new Button("Modify");
		modifyButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		modifyButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processActionModify(e);  
            	  }
        	});
		
		deleteButton = new Button("Delete");
		deleteButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		deleteButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processActionDelete(e);  
            	  }
        	});
		
		cancelButton = new Button("Back");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("CancelSearch", null);
            	  }
        	});
		doneCont.getChildren().add(modifyButton);
		doneCont.getChildren().add(deleteButton);
		doneCont.getChildren().add(cancelButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}

	// Create the status log field
	//-------------------------------------------------------------
	private MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	
	//-------------------------------------------------------------
	public void populateFields()
	{
		Barcode.setText((String)myModel.getState("Barcode"));
		InventoryItemTypeName.setText((String)myModel.getState("InventoryItemTypeName"));
		InventoryItemTypeName.setEditable(false);
		Vendor.setText((String)myModel.getState("Vendor"));
		DateRecieved.setText((String)myModel.getState("DateRecieved"));
		DateLastUsed.setText((String)myModel.getState("DateLastUsed"));
		Notes.setText((String)myModel.getState("Notes"));
	 	Status.setText((String)myModel.getState("Status"));
	}
	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processActionModify(Event evt)
	{
		// DEBUG: System.out.println("DepositAmountView.processAction()");

		clearErrorMessage();

		String actionChoice = "ModifyChoiceII";
		processData(actionChoice);
	}
	
	public void processActionDelete(Event evt)
	{
		// DEBUG: System.out.println("DepositAmountView.processAction()");

		clearErrorMessage();

		String actionChoice = "DeleteChoiceII";
		processData(actionChoice);
	}

	/**
	 * Process amount entered by user.
	 * Action is to pass this info on to the action object.
	 */
	//----------------------------------------------------------
	private void processData(String actionChoice)
	{
		myModel.stateChangeRequest(actionChoice, "action");
	}

	

	/**
	 * Required by interface
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		if(key.equals("UpdateStatusMessage"))
		{
			String msg = (String)value;
			displayMessage(msg);
		}
		else if(key.equals("ActionError"))
		{
			String msg = (String)value;
			displayErrorMessage(msg);
		}
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
	 * Display message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
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