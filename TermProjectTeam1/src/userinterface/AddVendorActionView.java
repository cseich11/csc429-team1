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

/** The class containing the Deposit Amount View  for the ATM application */
//==============================================================
public class AddVendorActionView extends View
{

	// Model

	// GUI components
	protected TextField venName, phoneNum;

	private Button submitButton;
	private Button doneButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public AddVendorActionView(IModel model)
	{
		super(model, "AddVendorActionView");

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
        
        Text prompt = new Text("VENDOR INFORMATION");
        prompt.setWrappingWidth(400);
        prompt.setTextAlignment(TextAlignment.CENTER);
        prompt.setFill(Color.BLACK);
        grid.add(prompt, 0, 0, 2, 1);

		Text vendorNameLabel = new Text(" Vendor Name : ");
		Font myFont = Font.font("Helvetica", FontWeight.BOLD, 12);
		vendorNameLabel.setFont(myFont);
		vendorNameLabel.setWrappingWidth(150);
		vendorNameLabel.setTextAlignment(TextAlignment.RIGHT);
		grid.add(vendorNameLabel, 0, 1);

		venName = new TextField();
		venName.setEditable(true);
		venName.setOnAction(e -> {
			processAction(e);
		});
		grid.add(venName, 1, 1);

		Text phoneNumber = new Text(" Phone Number : ");
		phoneNumber.setFont(myFont);
		phoneNumber.setWrappingWidth(150);
		phoneNumber.setTextAlignment(TextAlignment.RIGHT);
		grid.add(phoneNumber, 0, 2);

		phoneNum = new TextField();
		phoneNum.setOnAction(e -> {
			processAction(e);
		});
		grid.add(phoneNum, 1, 2);

		

		HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
		submitButton = new Button("Submit");
		submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		submitButton.setOnAction(e -> {
 			clearErrorMessage(); 
			// do the insert
			processAction(e);
        });
		
		doneButton = new Button("Back");
		doneButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		doneButton.setOnAction(e -> {
 			clearErrorMessage();
 			myModel.stateChangeRequest("Cancel", null);
        });
		doneCont.getChildren().add(submitButton);
		doneCont.getChildren().add(doneButton);
	
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
		venName.setText("");
		phoneNum.setText("");
	}

	// process events generated from our GUI components
	//-------------------------------------------------------------
	public void processAction(Event evt)
	{
		// DEBUG: System.out.println("DepositAmountView.processAction()");

		clearErrorMessage();

		String venNameEntered = venName.getText();
		String phoneNumEntered = phoneNum.getText();

		if (venNameEntered == null || venNameEntered.length() == 0)
			displayErrorMessage("Please enter an name");
		else if(phoneNumEntered == null || phoneNumEntered.length() == 0)
			displayErrorMessage("Please enter a phone number");
		else if(!phoneNumEntered.matches("^[0-9]{10}$")) 
			displayErrorMessage("Phone Number must be exactly 10-digits");
		else
		{
			String status = "Active";
			processData(venNameEntered, phoneNumEntered, status);
		}
	}

	/**
	 * Process amount entered by user.
	 * Action is to pass this info on to the action object.
	 */
	//----------------------------------------------------------
	private void processData(String a, String t, String s)
	{
		Properties props = new Properties();
		props.setProperty("vName", a);
		props.setProperty("vPhone", t);
		props.setProperty("vStatus", s);
		myModel.stateChangeRequest("VendorData", props);
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
