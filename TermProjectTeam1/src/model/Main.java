package model;

import event.Event;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;

public class Main extends Application
{

	/** Main frame of the application */
	private Stage mainStage;


	// start method for this class, the main application object
	//----------------------------------------------------------
	public void start(Stage primaryStage)
	{
	   System.out.println("Restaurant Inventory System");

           // Create the top-level container (main frame) and add contents to it.
	   MainStageContainer.setStage(primaryStage, "Restaurant Inventory System");
	   mainStage = MainStageContainer.getInstance();

	   // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
	   // 'X' IN THE WINDOW), and show it.
           mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
           });

           try
	   {
        	   new InventoryManager();
	   }
	   catch(Exception exc)
	   {
		System.err.println("Inventory.InventoryManager - could not create InventoryManager!");
		new Event(Event.getLeafLevelClassName(this), "Library.<init>", "Unable to create InventoryManager object", Event.ERROR);
		exc.printStackTrace();
	   }


  	   WindowPosition.placeCenter(mainStage);

           mainStage.show();
	}


	/** 
	 * The "main" entry point for the application. Carries out actions to
	 * set up the application
	 */
	//----------------------------------------------------------
    	public static void main(String[] args)
	{

		launch(args);
	}

}
