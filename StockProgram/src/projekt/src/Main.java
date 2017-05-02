package projekt.src;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

	//Applicationi standard laius, pikkus.
	public static int width = 800;
	public static int height = 480;
	
	//Aktsia sisestuse tekstiväli.
	TextField stockSymbolInput = new TextField();
	Button addStock;
	
	ObservableList stocksListItems = FXCollections.observableArrayList();//Lisame aktsia ListView objekti.
	ListView stockListView;
	
	StockInfoPanel stockInfoPanel;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();
		//Borderpanel - põhi paneel kuhu lähevad kõik teised elemendid.
		BorderPane borderPanel = new BorderPane();
		//TopPaneli lähevad kõik päise(header) elemendid.
		HBox topPanel = new HBox();
		topPanel.setPrefHeight(50);
		topPanel.setStyle("-fx-background-color: #b3e6ff;");
		Label title = new Label("Aktsiate informatsioon");
		title.setFont(new Font("Calibri", 18));
		topPanel.getChildren().add(title);
		
		//Kesk paneel jaguneb kaheks(vasak, parem). Parem paneel näitab aktsia informatsiooni, vasakul on aktsia sisestus.
		HBox centerPanel = new HBox(20);
		centerPanel.setStyle("-fx-background-color: #e6f7ff;");
		centerPanel.setPadding(new Insets(10));
		VBox leftSidePanel = new VBox();
		leftSidePanel.setSpacing(10);
		HBox stockInputPanel = new HBox();
		stockInputPanel.setSpacing(15);
		
		addStock = new Button("Lisa");
		addStock.setPrefSize(100, 20);
		addStock.setOnAction(new OnButtonClicked());
		
		Label listLabel = new Label("Olemas olevad aktsiad:");
		listLabel.setFont(new Font("Calibri", 18));
		stockListView = new ListView<>(stocksListItems);
		stockListView.getSelectionModel().selectedItemProperty().addListener(new OnListViewItemChange());
		
		
		stockInputPanel.getChildren().addAll(new Label("Lisa akstsia:"), stockSymbolInput, addStock);
		leftSidePanel.getChildren().addAll(stockInputPanel, listLabel, stockListView);
		
		stockInfoPanel = new StockInfoPanel();
		
		centerPanel.getChildren().addAll(leftSidePanel, stockInfoPanel);
		
		//TODO lisada palju jama siia veel...
		
		//Lisame paneelid põhi paneelile.
		borderPanel.setTop(topPanel);
		borderPanel.setCenter(centerPanel);
		Scene scene = new Scene(borderPanel, width, height);
		primaryStage.setScene(scene);
		//Akna sulgemisel programm sulgeks.
		primaryStage.setOnCloseRequest(e -> closeApplication());
		primaryStage.show();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		// TODO load data from files.
		
	}
	
	private void closeApplication(){
		// TODO Save data to file.
		Platform.exit();
	}

	/** OnButtonClicked käsitleb nuppude vajutust. */
	private class OnButtonClicked implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			if(e.getSource() == addStock){
				if(!stockSymbolInput.getText().isEmpty()){
					List<String> dataFromXML = XMLParser.getStockByQuote(stockSymbolInput.getText());
					if(!dataFromXML.get(0).isEmpty() && !dataFromXML.get(1).isEmpty() && !dataFromXML.get(2).isEmpty() && !dataFromXML.get(3).isEmpty()){
						System.out.println(dataFromXML.get(0) + " " + dataFromXML.get(1) + " " + dataFromXML.get(2));
						new Stock(dataFromXML.get(0), Double.parseDouble(dataFromXML.get(1)), Double.parseDouble(dataFromXML.get(2)),
									Double.parseDouble(dataFromXML.get(3)));
						//Lisan aktsia firma nime ArrayListi, mis kuvab aktsia ListView-s.
						stocksListItems.add(dataFromXML.get(0));
						
						//TODO Save to file...
					} else {
						//TODO Aktsiat ei eksisteeri...
					}
				} else {
					
				}
			}
		}
		
	}
	
	private class OnListViewItemChange implements ChangeListener<String> {

		@Override
		public void changed(ObservableValue<? extends String> arg0, String oldVal, String newVal) {
			// TODO Auto-generated method stub
			for(Stock s : Stock.stocks) {
				if(s.getName().equals(newVal)) {
					stockInfoPanel.setStockName(s.getName());
					stockInfoPanel.setCurrentPrice(s.getCurrentPrice());
					stockInfoPanel.setDaysLow(s.getDaysLow());
					stockInfoPanel.setDaysMax(s.getDaysMax());
					break;
				}
			}
			
		}
		
	}

}
