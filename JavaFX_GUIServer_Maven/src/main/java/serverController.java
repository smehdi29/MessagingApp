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

public class serverController implements Initializable {

	
	//server screen
	
	@FXML
	ListView<String> listItems = new ListView<String>();;	
	
	@FXML
	BorderPane pane;

	

	Server serverConnection;

	Stage currStage;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		serverConnection = new Server(data -> {
			Platform.runLater(()->{
				listItems.getItems().add(data.toString());
			});
		});
		
	}
	
}
