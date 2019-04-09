// specify the package
package userinterface;

// system imports
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

import java.util.Enumeration;
import java.util.Properties;

// project imports
import impresario.IModel;

/** The class containing the InsertNewBook View  for the ATM application */
//==============================================================
public class ModifyIITActionView extends View
{

	// GUI components
	protected TextField itemTypeName;
	protected TextField units;
	protected TextField unitMeasure;
	protected TextField validityDays;
	protected TextField reorderPoint;
	protected TextField notes;
	
	protected ComboBox<String> status;

	protected Button cancelButton;
	protected Button submitButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ModifyIITActionView(IModel model)
	{
		super(model, "ModifyIITActionView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());
		
		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             \n              "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("UpdateStatusMessage", this);
		myModel.subscribe("ActionMessage", this);
	}


	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

		Text titleText = new Text(" Brockport Restaraunt ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);
		
		return container;
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
        
        Text prompt = new Text("INVENTORY ITEM TYPE INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text iitNameLabel = new Text(" Item Type Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		iitNameLabel.setFont(myFont);
		iitNameLabel.setWrappingWidth(150);
		iitNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(iitNameLabel, 0, 1);

		itemTypeName = new TextField();
		itemTypeName.setEditable(true);
		grid.add(itemTypeName, 1, 1);
		itemTypeName.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processAction(e);  
            	  }
        	});

		Text unitsLabel = new Text(" Units : ");
		unitsLabel.setFont(myFont);
		unitsLabel.setWrappingWidth(150);
		unitsLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(unitsLabel, 0, 2);

		units = new TextField();
		units.setEditable(true);
		grid.add(units, 1, 2);
		units.setOnAction(new EventHandler<ActionEvent>() {

  		     @Override
  		     public void handle(ActionEvent e) {
  		    	clearErrorMessage();
  		    	processAction(e);  
       	  }
   	});

		Text unitMeasureLabel = new Text(" Unit Measure : ");
		unitMeasureLabel.setFont(myFont);
		unitMeasureLabel.setWrappingWidth(150);
		unitMeasureLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(unitMeasureLabel, 0, 3);

		unitMeasure = new TextField();
		unitMeasure.setEditable(true);
		grid.add(unitMeasure, 1, 3);
		unitMeasure.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processAction(e);  
            	  }
        	});

		Text validityDaysLabel = new Text(" Validity Days : ");
		validityDaysLabel.setFont(myFont);
		validityDaysLabel.setWrappingWidth(150);
		validityDaysLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(validityDaysLabel, 0, 4);

		validityDays = new TextField();
		validityDays.setEditable(true);
		grid.add(validityDays, 1, 4);
		validityDays.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processAction(e);  
            	  }
        	});
		
		Text reorderPointLabel = new Text(" Reorder Point : ");
		reorderPointLabel.setFont(myFont);
		reorderPointLabel.setWrappingWidth(150);
		reorderPointLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(reorderPointLabel, 0, 5);

		reorderPoint = new TextField();
		reorderPoint.setEditable(true);
		grid.add(reorderPoint, 1, 5);
		reorderPoint.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processAction(e);  
            	  }
        	});
		
		Text notesLabel = new Text(" Notes : ");
		notesLabel.setFont(myFont);
		notesLabel.setWrappingWidth(150);
		notesLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(notesLabel, 0, 6);

		notes = new TextField();
		notes.setEditable(true);
		grid.add(notes, 1, 6);
		notes.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processAction(e);  
            	  }
        	});
		
		Text statusLabel = new Text(" Status : ");
		statusLabel.setFont(myFont);
		statusLabel.setWrappingWidth(150);
		statusLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(statusLabel, 0, 7);
		status= new ComboBox<String>();
		status.getItems().addAll("Active", "Inactive");
		grid.add(status, 1, 7);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	processAction(e);  
            	  }
        	});
		
		cancelButton = new Button("Back");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(new EventHandler<ActionEvent>() {

       		     @Override
       		     public void handle(ActionEvent e) {
       		    	clearErrorMessage();
       		    	myModel.stateChangeRequest("CancelModify", null);
            	  }
        	});
		doneCont.getChildren().add(submitButton);
		doneCont.getChildren().add(cancelButton);
	
		vbox.getChildren().add(grid);
		vbox.getChildren().add(doneCont);

		return vbox;
	}

	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		itemTypeName.setText((String)myModel.getState("ItemTypeName"));
		itemTypeName.setEditable(false);
		units.setText((String)myModel.getState("Units"));
		unitMeasure.setText((String)myModel.getState("UnitMeasure"));
		validityDays.setText((String)myModel.getState("ValidityDays"));
		reorderPoint.setText((String)myModel.getState("ReorderPoint"));
		notes.setText((String)myModel.getState("Notes"));
	 	status.getSelectionModel().select((String)myModel.getState("Status"));
	}

	/**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{
		clearErrorMessage();

		if (key.equals("UpdateStatusMessage") == true)
		{
			displayMessage((String)value);
		}
		else
			if (key.equals("ActionMessage") == true)
			{
				String val = (String)value;
				if (val.startsWith("ERR") == true)
					displayErrorMessage(val);
				else
					displayMessage(val);
				
			}
	}

	public void processAction(ActionEvent e)
	{
		clearErrorMessage();
		
		String itemTypeNameInput = itemTypeName.getText();
		String unitsInput = units.getText();
		String unitMeasureInput = unitMeasure.getText();
		String validityDaysInput = validityDays.getText();
		String reorderPointInput = reorderPoint.getText();
		String notesInput = notes.getText();
		String statusInput = status.getValue();
		int u, v, r;
		boolean uC = true, vC = true, rC = true;
		try {
			u = Integer.parseInt(unitsInput);
		} catch(NumberFormatException d) {
			uC = false;
		}
		try {
			v = Integer.parseInt(validityDaysInput);
		} catch(NumberFormatException f) {
			vC = false;
		}
		try {
			r = Integer.parseInt(reorderPointInput);
		} catch(NumberFormatException g) {
			rC = false;
		}
		
		if(itemTypeNameInput.length() == 0)
		{
			displayErrorMessage("Please Enter an Item Type Name");
		}
		else if(!uC || unitsInput.length() == 0 || Integer.parseInt(unitsInput) < 0)
		{
			displayErrorMessage("Please Enter a nonnegative amount of units");
		}
		else if(unitMeasureInput.length() == 0)
		{
			displayErrorMessage("Please Enter a measure for units");
		}
		else if(!vC || validityDaysInput.length() == 0 || Integer.parseInt(unitsInput) < 0)
		{
			displayErrorMessage("Please Enter a nonnegative number for validity days");
		}
		else if(!rC || reorderPointInput.length() == 0 || Integer.parseInt(unitsInput) < 0)
		{
			displayErrorMessage("Please Enter a nonnegative number for reorder point");
		}
//		else if(notesInput.length() == 0)
//		{
//			displayErrorMessage("Please Enter notes");
//		}
		else
		{
			processData(itemTypeNameInput, unitsInput, unitMeasureInput, validityDaysInput, reorderPointInput, notesInput, statusInput);
		}
	}
	
	
	private void processData(String itemTypeName, String units, String unitMeasure, String validityDays, String reorderPoint, String notes, String status)
	{
		Properties prop = new Properties();
		prop.setProperty("ItemTypeName", itemTypeName);
		prop.setProperty("Units", units);
		prop.setProperty("UnitMeasure", unitMeasure);
		prop.setProperty("ValidityDays", validityDays);
		prop.setProperty("ReorderPoint", reorderPoint);
		prop.setProperty("Notes", notes);
		prop.setProperty("Status", status);
		myModel.stateChangeRequest("ModifyIITData", prop);
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
	 * Display info message
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

//---------------------------------------------------------------
//	Revision History:
//



