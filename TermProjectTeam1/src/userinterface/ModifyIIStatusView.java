package userinterface;

//system imports
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
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
import model.InventoryManager;
import model.SearchVendorAction;
import model.Vendor;

import java.util.Properties;

//project imports
import impresario.IModel;

public class ModifyIIStatusView extends View
{

  private Button cancelButton;
  private Button submitButton;
  private ComboBox<String> status;

  // For showing error message

  private MessageView statusLog;
  SearchVendorAction searchV;
	
	// constructor for this class -- takes a model object
	//----------------------------------------------------------
  public ModifyIIStatusView( IModel model)
  {

  	super(model, "ModifyIIStatusView");

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

  // Create the label (Text) for the title of the screen
  //-------------------------------------------------------------
  private Node createTitle()
  {
		
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);	

      Text titleText = new Text("Modify Inventory Item Status");
      titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setStrokeWidth(0.5);
		titleText.setStroke(Color.GOLDENROD);  
      titleText.setTextAlignment(TextAlignment.CENTER);
      titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

      return container;
  }

  // Create the main form contents
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

      Text vStatus = new Text("STATUS");
      vStatus.setFont(Font.font("Helvetica", FontWeight.BOLD, 13));
      vStatus.setWrappingWidth(150);
      vStatus.setTextAlignment(TextAlignment.CENTER);
      vStatus.setFill(Color.BLACK);
      grid.add(vStatus, 0, 2);
		
		status = new ComboBox<String>();
      status.getItems().addAll("Used", "Available", "Expired");
      grid.add(status, 1, 2);

      HBox doneCont = new HBox(10);
		doneCont.setAlignment(Pos.CENTER);
      submitButton = new Button("Submit");
      submitButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
      submitButton.setOnAction(e -> {
			clearErrorMessage(); 
			// do the insert
			processAction();
      });

      cancelButton = new Button("Back");
      cancelButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
      cancelButton.setOnAction(e -> {
			clearErrorMessage();
			myModel.stateChangeRequest("CancelModify", null);
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
	 	status.getSelectionModel().select((String)myModel.getState("Status"));
	}

  //---------------------------------------------------------
  public void updateState(String key, Object value)
  {
      // STEP 6: Be sure to finish the end of the 'perturbation'
      // by indicating how the view state gets updated.
      if (key.equals("UpdateStatusMessage") == true)
      {
          // display the passed text
      	String val = (String)value;
			if (val.startsWith("ERR") == true)
				displayErrorMessage(val);
			else
				displayMessage(val);
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
  
  //----------------------------------------------------------
  private void processAction() 
  {
  	clearErrorMessage();
  	
		String statusEntered = (String)status.getValue();
		
		processData(statusEntered);
  }
  
  private void processData(String s) 
  {
  	Properties props = new Properties();
		props.setProperty("status", s);
		
		//System.out.println(n + " - " + num + " - " + s); //DEBUG
		
		myModel.stateChangeRequest("ModifyIIStatus", props);
		//populateFields();
  }
}
