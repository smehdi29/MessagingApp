
import java.io.IOException;
import java.net.URL;
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

public class MyController implements Initializable {

	//home screen
	
	@FXML
	BorderPane homePane;

	@FXML
	HBox homeBox;
	
	@FXML
	Button clientChoice;

	@FXML
	Button serverChoice;
	
	

	Stage currStage;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public void setServer(ActionEvent e) {
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Server.fxml"));
			Scene serverScene = new Scene(root, 900, 900);
			
			currStage = (Stage) serverChoice.getScene().getWindow();
			currStage.setScene(serverScene);
	        serverScene.getStylesheets().add("/styles/serverStyle.css");
			currStage.setTitle("This is the Server");
			
			serverChoice.setDisable(true);
		} 
		catch (IOException e1) {
			e1.printStackTrace();
	        System.exit(1);
		}
	}
	
	public void setClient(ActionEvent e) {
			
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/Client.fxml"));
			Scene clientScene = new Scene(root, 900, 900);
			currStage = (Stage) serverChoice.getScene().getWindow();
			currStage.setScene(clientScene);
	        clientScene.getStylesheets().add("/styles/clientStyle.css");
			currStage.setTitle("This is a Client");
			clientChoice.setDisable(true);

		} catch (IOException e1) {
			e1.printStackTrace();
	        System.exit(1);
		}
	}
}
	