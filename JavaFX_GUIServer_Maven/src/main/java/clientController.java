import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class clientController implements Initializable {
	
	//client screen

	@FXML
	HBox clientHorizontalBox;
	
	@FXML
	VBox clientBox;
	
	@FXML	
	TextField c1;
	
	@FXML
	Button b1;
	
	@FXML
	ListView<String> listItems2 = new ListView<String>();
	
	@FXML
	VBox clientRightSideBox;
	
	@FXML
	ListView<String> activeListView = new ListView<String>();
	
	@FXML
	HBox selectorBox;
	
	@FXML
	VBox selectorInputBox;
	
	@FXML
	TextField selectorField;
	
	@FXML
	Button selectorButton;
	
	@FXML
	Button clear;
	
	@FXML
	ListView<String> selectedClients = new ListView<String>();
	

	
	Client clientConnection;

	Stage currStage;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientConnection = new Client(data->{
			Platform.runLater(()->{
				
				
				listItems2.getItems().add(data.toString());
				ArrayList<String> activ = data.active;
				activeListView.getItems().clear();
				for(String c : activ) {
					activeListView.getItems().add(c);
				}
				
				/*if(!selectedClients.getItems().isEmpty()) {
					for(String c : selectedClients.getItems()) {
						if(!clientConnection.specific.contains(c)) {
							selectedClients.getItems().remove(c);
						}
					}
				}*/
				
			});
		});
		
		clientConnection.start();

		
	}

	private int clientRemover(int target) {
		int index = 0;
		for(String client : activeListView.getItems()) {
			if(client.equals("Client #" + target)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	public void specificClientChooser() {
		
		if( isNumeric(selectorField.getText()) && activeListView.getItems().contains("Client: #" + Integer.valueOf(selectorField.getText()))) {
			//if(clientConnection.specific == null)
				//clientConnection.specific = new ArrayList<>();
			clientConnection.specific.add(Integer.valueOf(selectorField.getText()));
			
			selectedClients.getItems().add("Client: #" + selectorField.getText());
			selectorField.clear();
		}
		else{
			/*for(String c : activeListView.getItems()) {
				System.out.println(c);
			}*/
			selectorField.clear();
			selectorField.setText("Unavailable");
		}
	}
	
	private boolean isNumeric(String specifics) {
	    if (specifics == null) {
	        return false;
	    }
	    try {
	        Integer d = Integer.parseInt(specifics);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public void sendEveryoneMessage() {
		clientConnection.send(c1.getText());
		c1.clear();
	}
	
	public void sendSpecificMessage() {
		String specifiedClients = "$";
		for(String client : selectedClients.getItems()) {
			specifiedClients += client.substring(8) + " ";
		}
		clientConnection.send(specifiedClients + ": " + c1.getText());
		c1.clear();
	}
	
	public void sendMessage() {
		
		sendEveryoneMessage();
		
		
	}
	
	public void clearSelectedClients() {
		clientConnection.specific.clear();
		
		
		selectedClients.getItems().clear();
		
	}
	
}
