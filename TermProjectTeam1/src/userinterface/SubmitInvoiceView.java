// specify the package
package userinterface;

import javafx.event.ActionEvent;
// system imports
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

	private Button submitButton;
	private Button cancelButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SubmitInvoiceView(IModel model)
	{
		super(model, "SubmitInvoiceView");

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
		myModel.subscribe("ActionError", this);
	}


	// Create the label (Text) for the title
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
        
        Text prompt = new Text("PROCESS INVOICE");
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

		iitNameField = new TextField();
        iitNameField.setEditable(true);
        iitNameField.setOnAction(e -> {
			processAction(e);
		});
		grid.add(iitNameField, 1, 1);

		Text barcodeLabel = new Text(" Item Barcode : ");
		barcodeLabel.setFont(myFont);
		barcodeLabel.setWrappingWidth(150);
		barcodeLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(barcodeLabel, 0, 2);

		barcodeField = new TextField();
        barcodeField.setOnAction(e -> {
			processAction(e);
		});
		grid.add(barcodeField, 1, 2);

		Text notesLabel = new Text(" Item Notes : ");
		notesLabel.setFont(myFont);
		notesLabel.setWrappingWidth(150);
		notesLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(notesLabel, 0, 3);

		notesField = new TextField();
        notesField.setOnAction(e -> {
			processAction(e);
		});
		grid.add(notesField, 1, 3);

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(e -> {
			clearErrorMessage();
       		processAction(e);
        });
		
		cancelButton = new Button("Back");
		cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		cancelButton.setOnAction(e -> {
			clearErrorMessage();
			myModel.stateChangeRequest("Cancel", null);
        });
		
		doneCont.getChildren().add(submitButton);
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
			displayErrorMessage("Please enter a valid item type name");
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
		props.setProperty("i", i);
		props.setProperty("b", b);
		props.setProperty("n", n);
		myModel.stateChangeRequest("InvoiceData", props);
		populateFields();
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
		else if (key.equals("ActionError"))
			displayErrorMessage((String)value);
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
