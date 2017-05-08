package projekt.src;

import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

public class Main extends Application {

	//Applicationi standard laius, pikkus.
	private static String name = "Aktsialeidja";
	public static int width = 800;
	public static int height = 480;
	
	//Aktsia sisestuse tekstiväli.
	TextField stockSymbolInput = new TextField();
	Button addStock;
	
	ObservableList<String> stocksListItems = FXCollections.observableArrayList();//Lisame aktsia ListView objekti.
	ListView<String> stockListView;
	
	StockInfoPanel stockInfoPanel;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();
		//Borderpanel - põhipaneel, kuhu lähevad kõik teised elemendid.
		BorderPane borderPanel = new BorderPane();
		//TopPaneli lähevad kõik päise(header) elemendid.
		HBox topPanel = new HBox();
		topPanel.setPrefHeight(50);
		topPanel.setStyle("-fx-background-color: #b3e6ff;");
		Label title = new Label("Aktsiate informatsioon");
		title.setFont(new Font("Calibri", 18));
		topPanel.getChildren().add(title);
		
		//Keskpaneel jaguneb kaheks(vasak, parem). Parem paneel näitab aktsia informatsiooni, vasakul on aktsia sisestus.
		HBox centerPanel = new HBox(20);
		centerPanel.setStyle("-fx-background-color: #e6f7ff;");
		centerPanel.setPadding(new Insets(10));
		VBox leftSidePanel = new VBox();
		leftSidePanel.setSpacing(10);
		HBox stockInputPanel = new HBox();
		stockInputPanel.setSpacing(15);
		
		addStock = new Button("Lisa");
		addStock.setPrefSize(100, 20);
		addStock.setOnAction(new OnButtonClicked(this));
		
		Label listLabel = new Label("Olemas olevad aktsiad:");
		listLabel.setFont(new Font("Calibri", 18));
		stockListView = new ListView<>(stocksListItems);
		stockListView.getSelectionModel().selectedItemProperty().addListener(new OnListViewItemChange());
		
		
		stockInputPanel.getChildren().addAll(new Label("Lisa akstsia:"), stockSymbolInput, addStock);
		leftSidePanel.getChildren().addAll(stockInputPanel, listLabel, stockListView);
		
		stockInfoPanel = new StockInfoPanel();
		
		centerPanel.getChildren().addAll(leftSidePanel, stockInfoPanel);
		
		//TODO lisada palju jama siia veel...
		
		//Lisame paneelid põhipaneelile.
		borderPanel.setTop(topPanel);
		borderPanel.setCenter(centerPanel);
		Scene scene = new Scene(borderPanel, width, height);
		primaryStage.setScene(scene);
		primaryStage.setTitle(name);
		//Akna sulgemisel programm sulgetakse.
		primaryStage.setOnCloseRequest(e -> closeApplication());
		primaryStage.show();
		
		//peatsukkel
		long updateTime = 10000; //millisekundit; XML'i lugemine votab palju, ei saa liiga tihti uuendada.
		Timeline mainLoop = new Timeline(new KeyFrame(Duration.millis(updateTime), (event -> update())));
		mainLoop.setCycleCount(Timeline.INDEFINITE);
		mainLoop.play();
	}
	
	private void initialize() {
		// TODO Auto-generated method stub
		// TODO load data from files.
		List<String> stockSymbols = FileManager.getStockSymbolsFromFile();
		for(String stockSymbol : stockSymbols){
			addStockToList(stockSymbol);
		}
	}
	
	private void closeApplication(){
		// TODO Save data to file.
		Platform.exit();
	}
	
	private void update() {
		String stock_name = stockListView.getSelectionModel().getSelectedItem(); //valitud aktsia
		Calendar calendar = new GregorianCalendar(); //aeg + kalender
		
		for(Stock stock: Stock.stocks) {
			if(stock.getName().equals(stock_name)) {
				//saa uued andmed
				List<String> dataFromXML = XMLParser.getStockByQuote(stock.getTag());
				stock.setCurrentPrice(Double.parseDouble(dataFromXML.get(2)));
				stock.setDaysLow(Double.parseDouble(dataFromXML.get(3)));
				stock.setDaysMax(Double.parseDouble(dataFromXML.get(4)));
				
				//asenda vanad andmed
				stockInfoPanel.setStockName(stock.getName()); //pole uldjuhul vajalik
				stockInfoPanel.setCurrentPrice(stock.getCurrentPrice());
				stockInfoPanel.setDaysLow(stock.getDaysLow());
				stockInfoPanel.setDaysMax(stock.getDaysMax());
				
				//uuenda kasutusel olev graafik
				stockInfoPanel.updateActiveGraph(calendar, stock.getCurrentPrice());
				
				break;
			}
		}
	}
	
	/** Võtab aktsia informatsiooni XML failist ning lisab selle listi. */
	public void addStockToList(String symbol){
		List<String> dataFromXML = XMLParser.getStockByQuote(symbol);
		if(!dataFromXML.get(0).isEmpty() && !dataFromXML.get(1).isEmpty() && !dataFromXML.get(2).isEmpty() && !dataFromXML.get(3).isEmpty()){
			if(stocksListItems.contains(dataFromXML.get(0)))
				return;
			Stock stock = new Stock(dataFromXML.get(0), dataFromXML.get(1), Double.parseDouble(dataFromXML.get(2)), Double.parseDouble(dataFromXML.get(3)),
						Double.parseDouble(dataFromXML.get(4)));
			//Lisan aktsia firma nime ArrayListi, mis kuvab aktsia ListView-s.
			stocksListItems.add(dataFromXML.get(0));
			
			//TODO Save to file...
			FileManager.saveStockToFile(stock);
		} else {
			//TODO Aktsiat ei eksisteeri...
		}
	}

	/** OnButtonClicked käsitleb nuppude vajutust. */
	private class OnButtonClicked implements EventHandler<ActionEvent> {

		Main main;
		
		public OnButtonClicked(Main main) {
			this.main = main;
		}
		
		@Override
		public void handle(ActionEvent e) {
			if(e.getSource() == addStock){
				if(!stockSymbolInput.getText().isEmpty()){
					main.addStockToList(stockSymbolInput.getText());
					//TODO Check if stock already exists.
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
