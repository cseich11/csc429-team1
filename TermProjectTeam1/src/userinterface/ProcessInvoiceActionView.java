package userinterface;

import impresario.IModel;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ProcessInvoiceActionView extends View {

	// GUI components
		private TextField nameSearch, notesSearch;

		private Button submitButton;
		private Button cancelButton;

		// For showing error message
		private MessageView statusLog;

		// constructor for this class -- takes a model object
		//----------------------------------------------------------
		public ProcessInvoiceActionView(IModel action)
		{
			super(action, "SearchIITActionView");


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
