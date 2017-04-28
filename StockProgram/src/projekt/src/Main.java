package projekt.src;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	public static int width = 800;
	public static int height = 480;
	
	TextField stockSymbolInput = new TextField();
	Button addStock;
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException{
		System.out.println(XMLParser.getStockByQuote("158d"));
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		BorderPane borderPanel = new BorderPane();
		HBox topPanel = new HBox();
		Label title = new Label("Aktsiate informatsioon");
		title.setFont(new Font("Calibri", 18));
		topPanel.getChildren().add(title);
		
		HBox centerPanel = new HBox();
		VBox leftSidePanel = new VBox();
		HBox stockInputPanel = new HBox();
		addStock = new Button("Lisa");
		addStock.setOnAction(new OnButtonClicked());
		stockInputPanel.getChildren().addAll(new Label("Lisa akstsia:"), stockSymbolInput, addStock);
		leftSidePanel.getChildren().add(stockInputPanel);
		centerPanel.getChildren().add(leftSidePanel);
		
		borderPanel.setTop(topPanel);
		borderPanel.setCenter(centerPanel);
		Scene scene = new Scene(borderPanel, width, height);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		primaryStage.show();
	}
	
	private class OnButtonClicked implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			if(e.getSource() == addStock){
				if(!stockSymbolInput.getText().isEmpty()){
					
				} else {
					
				}
			}
		}
		
	}

}
