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
public class SearchVendorActionView extends View
{

	// Model

	// GUI components
	protected TextField venName, phoneNum;
	private ComboBox<String> status;

	private Button submitButton;
	private Button doneButton;

	// For showing error message
	private MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public SearchVendorActionView(IModel SearchVendorAction)
	{
		super(SearchVendorAction, "SearchVendorActionView");

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
	}


	// Create the label (Text) for the title
	//-------------------------------------------------------------
	private Node createTitle()
	{
		
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

        Text titleText = new Text("Search For Vendor");
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




        Text vendorName = new Text("Enter Vendor Name:");
		vendorName.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        vendorName.setWrappingWidth(350);
        vendorName.setTextAlignment(TextAlignment.CENTER);
        vendorName.setFill(Color.GOLDENROD);
        grid.add(vendorName, 1, 0);

		venName = new TextField();
		venName.setEditable(true);
		venName.setOnAction(e -> {
			processAction(e);
		});
		grid.add(venName, 1, 1);
		
		
		
		Text phoneNumber = new Text("Enter Phone Number:");
		phoneNumber.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        phoneNumber.setWrappingWidth(350);
        phoneNumber.setTextAlignment(TextAlignment.CENTER);
        phoneNumber.setFill(Color.GOLDENROD);
        grid.add(phoneNumber, 1, 2);
		
		phoneNum = new TextField();
		phoneNum.setOnAction(e -> {
			processAction(e);
		});
		grid.add(phoneNum, 1, 3);
		
		
		
		submitButton = new Button("Submit");
 		submitButton.setOnAction(e -> {
 			clearErrorMessage(); 
			// do the insert
			processAction(e);
        });

		doneButton = new Button("Done");
 		doneButton.setOnAction(e -> {
 			clearErrorMessage();
			myModel.stateChangeRequest("CancelInsert", null);   
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

		boolean isNum = true;
		try {
			Integer.parseInt(phoneNumEntered);
		} catch(NumberFormatException e) {
			isNum = false;
		}

		if (venNameEntered == null || venNameEntered.length() == 0)
			displayErrorMessage("Please enter an name");
		else if(phoneNumEntered == null || phoneNumEntered.length() == 0)
			displayErrorMessage("Please enter a phone number");
		else if(!isNum || phoneNumEntered.length() != 9)
			displayErrorMessage("Phone Number must be exactly 9-digits");
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
		props.setProperty("vendorName", a);
		props.setProperty("phoneNumber", t);
		props.setProperty("status", t);
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
