// specify the package
package userinterface;

// system imports
import javafx.event.Event;
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

import java.util.Properties;

// project imports
import impresario.IModel;
//import model.SearchVendorAction;

/** The class containing the Deposit Amount View  for the ATM application */
//==============================================================
public class SubmitInvoiceView extends View
{

	// Model

	// GUI components
	protected TextField iitNameField, barcodeField, notesField;
	private ComboBox<String> status;

	private Button submitButton;
	private Button doneButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SubmitInvoiceView(IModel action)
	{
		super(action, "SubmitInvoiceView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// create our GUI components, add them to this panel
		container.getChildren().add(createTitle());
		container.getChildren().add(createFormContent());

		// Error message area
		container.getChildren().add(createStatusLog("                          \n                            "));

		getChildren().add(container);

		populateFields();
		
		myModel.subscribe("UpdateStatusMessage", this);
		myModel.subscribe("ActionError", this);
	}


	// Create the label (Text) for the title
	//-------------------------------------------------------------
	private Node createTitle()
	{
		
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

        Text titleText = new Text("Process Invoice");
        titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setStrokeWidth(0.5);
		titleText.setStroke(Color.GOLDENROD);  
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
        grid.setPadding(new Insets(25, 40, 1, 25));




        Text iitName = new Text("Enter Inventory Item Type Name:");
        iitName.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        iitName.setWrappingWidth(350);
        iitName.setTextAlignment(TextAlignment.CENTER);
        iitName.setFill(Color.GOLDENROD);
        grid.add(iitName, 1, 0);

        iitNameField = new TextField();
        iitNameField.setEditable(true);
        iitNameField.setOnAction(e -> {
			processAction(e);
		});
		grid.add(iitNameField, 1, 1);
		
		
		
		Text barcode = new Text("Enter Inventory Item Barcode:");
		barcode.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		barcode.setWrappingWidth(350);
		barcode.setTextAlignment(TextAlignment.CENTER);
		barcode.setFill(Color.GOLDENROD);
        grid.add(barcode, 1, 2);
		
        barcodeField = new TextField();
        barcodeField.setOnAction(e -> {
			processAction(e);
		});
		grid.add(barcodeField, 1, 3);
		
		Text notes = new Text("Enter Inventory Item Notes:");
		notes.setFont(Font.font("Arial", FontWeight.BOLD, 13));
		notes.setWrappingWidth(350);
		notes.setTextAlignment(TextAlignment.CENTER);
		notes.setFill(Color.GOLDENROD);
        grid.add(notes, 1, 4);
		
        notesField = new TextField();
        notesField.setOnAction(e -> {
			processAction(e);
		});
		grid.add(notesField, 1, 5);
		
		
		
		submitButton = new Button("Submit");
 		submitButton.setOnAction(e -> {
 			clearErrorMessage(); 
			// do the insert
			processAction(e);
        });

		doneButton = new Button("Done");
 		doneButton.setOnAction(e -> {
 			clearErrorMessage();
			myModel.stateChangeRequest("Cancel", null);   
        });

		HBox btnContainer = new HBox(100);
		btnContainer.setAlignment(Pos.CENTER);
		btnContainer.getChildren().add(submitButton);
		btnContainer.getChildren().add(doneButton);

		vbox.getChildren().add(grid);
		vbox.getChildren().add(btnContainer);

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
		if(iitNameField.getText() == null)
			iitNameField.setText("");
		barcodeField.setText("");
		notesField.setText("");
	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(Event evt)
	{
		// DEBUG: System.out.println("DepositAmountView.processAction()");

		clearErrorMessage();

		String iitNameEntered = iitNameField.getText();
		String barcodeEntered = barcodeField.getText();
		String notesEntered = notesField.getText();
//		boolean isNum = true; 
//		try {
//			Integer.parseInt(phoneNumEntered);
//		} catch(NumberFormatException e) {
//			isNum = false;
//		}

		if (iitNameEntered == null || iitNameEntered.length() == 0)
			displayErrorMessage("Please enter an item type name");
		else if(barcodeEntered == null || barcodeEntered.length() == 0)
			displayErrorMessage("Please enter a barcode for the item on the invoice");
		else if(!barcodeEntered.matches("^[0-9]{9}$"))
			displayErrorMessage("Barcode entered must be numerical");
		else
		{
			processData(iitNameEntered, barcodeEntered, notesEntered);
		}
	}

	/**
	 * Process amount entered by user.
	 * Action is to pass this info on to the action object.
	 */
	//----------------------------------------------------------
	private void processData(String i, String b, String n)
	{
		Properties props = new Properties();
		props.setProperty("iitNameEntered", i);
		props.setProperty("barcodeEntered", b);
		props.setProperty("notesEntered", n);
		myModel.stateChangeRequest("InvoiceData", props);
		populateFields();
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
		else if(key.equals("ActionError")) {
			String msg = (String)value;
			displayMessage(msg);
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
